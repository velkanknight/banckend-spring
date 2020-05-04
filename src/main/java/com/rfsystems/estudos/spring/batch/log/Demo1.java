package com.rfsystems.estudos.spring.batch.log;


import com.rfsystems.estudos.spring.batch.mapper.LogFileRowMapper;
import com.rfsystems.estudos.spring.batch.model.LogDTO;
import com.rfsystems.estudos.spring.batch.model.LogEntity;
import com.rfsystems.estudos.spring.batch.processor.LogConvertProcessor;
import com.rfsystems.estudos.spring.batch.processor.FileDeletingTasklet;
import com.rfsystems.estudos.spring.batch.utils.FileReader;
import com.rfsystems.estudos.spring.batch.writer.LogDBWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import javax.sql.DataSource;

@Configuration
public class Demo1 {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private LogConvertProcessor docProcessor;
    private DataSource dataSource;
    private LogDBWriter logDBWriter;
    @Value( "${files.path}")
    private String path;

    @Autowired
    public Demo1(JobBuilderFactory jobBuilderFactory,
                 StepBuilderFactory stepBuilderFactory,
                 LogConvertProcessor docProcessor,
                 DataSource dataSource,
                 LogDBWriter logDBWriter){
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.docProcessor = docProcessor;
        this.dataSource = dataSource;
        this.logDBWriter = logDBWriter;
    }

    //criando o job
    @Qualifier(value = "demo1")
    @Bean
    public org.springframework.batch.core.Job demo1Job() throws Exception {
        return this.jobBuilderFactory.get("demo1")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .next(step2())
                .build();
    }

    //definindo o 1 step e passando o leitor, o processador e o escritor
    //adicionei um taskexecutor para melhorar a performance de execução com o multi-thread
    @Bean
    public Step step1() throws Exception {
        return this.stepBuilderFactory.get("step1")
                .<LogDTO, LogEntity>chunk(30)
                .reader(multiResourceItemReader())
                .processor(docProcessor)
                .writer(logDBWriter)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step step2() {
        FileDeletingTasklet task = new FileDeletingTasklet();
        task.setResources(FileReader.reader(path));
        return stepBuilderFactory.get("step2")
                .tasklet(task)
                .build();
    }

    @Bean
    @StepScope
    Resource inputFileResource(@Value("#{jobParameters[fileName]}") final String fileName) throws Exception {
        return new ClassPathResource(fileName);
    }
    //aqui adicionei um multi resource reader para aumentar a performance de leitura de multiplos arquivos
    @Bean
    @StepScope
    public MultiResourceItemReader<LogDTO> multiResourceItemReader()  {
        MultiResourceItemReader<LogDTO> resourceItemReader = new MultiResourceItemReader<LogDTO>();
        resourceItemReader.setResources(FileReader.reader(path));
        resourceItemReader.setDelegate(logReader());
        return resourceItemReader;
    }

    @Bean
    @StepScope
    public MultiResourceItemReader<LogDTO> multiResourceDeleteItemReader()  {
        MultiResourceItemReader<LogDTO> resourceItemReader = new MultiResourceItemReader<LogDTO>();
        FileReader.remove(path);
        return resourceItemReader;
    }

    //definindo os headers e delimitador das linhas do txt
    @Bean
    public FlatFileItemReader<LogDTO> logReader(){
        FlatFileItemReader<LogDTO> reader = new FlatFileItemReader<>();
        reader.setLineMapper(new DefaultLineMapper<LogDTO>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("data", "ip", "request", "status", "userAgent");
                setDelimiter("|");
            }});
            setFieldSetMapper(new LogFileRowMapper());
        }});
        return reader;
    }

    //definindo 4 theads para essa execução, aqui poderíamos ter outras abordagens, como processamento paralelo, porém,
    //seria necessário mais alguns testes de massa para definir a melhor opção
    @Bean
    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        simpleAsyncTaskExecutor.setConcurrencyLimit(2);
        return simpleAsyncTaskExecutor;
    }

}

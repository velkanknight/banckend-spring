package com.rfsystems.estudos.spring.batch.writer;

import com.rfsystems.estudos.spring.batch.model.LogEntity;
import com.rfsystems.estudos.spring.batch.repository.LogRepo;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LogDBWriter implements ItemWriter<LogEntity> {

    @Autowired
    private LogRepo logRepo;

    @Override
    public void write(List<? extends LogEntity> logs)  {
        try {
            System.out.println("Entrou writer");
            logRepo.saveAll(logs);
        } catch (Exception e) {
            System.out.println("Error ao tentar persistir registros no banco " + logs);
        }

    }
}

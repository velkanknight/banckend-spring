package com.rfsystems.estudos.spring.batch.scheduler;

import com.rfsystems.estudos.spring.batch.runner.JobRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalTime;

@Configuration
public class JobScheduler {

    private JobRunner jobRunner;

    public JobScheduler(JobRunner jobRunner){
        this.jobRunner = jobRunner;
    }

    //programando para o job ser executado a cada 3 min
//    @Scheduled(cron="0/10 * * 1/1 * ?")
    @Scheduled(cron="0 */1 * 1/1 * ?")
    public void jobScheduled(){
        System.out.println("Job sheduler executado as " + LocalTime.now().getHour() + "hrs " + LocalTime.now().getMinute() + "min");
        jobRunner.runBatchJob();
    }

}

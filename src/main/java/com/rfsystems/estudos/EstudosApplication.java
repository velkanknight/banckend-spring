package com.rfsystems.estudos;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
@ComponentScan(basePackages = {"com.rfsystems.estudos.spring.batch"})
public class EstudosApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstudosApplication.class, args);
	}

}

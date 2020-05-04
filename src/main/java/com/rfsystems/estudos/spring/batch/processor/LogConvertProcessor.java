package com.rfsystems.estudos.spring.batch.processor;

import com.rfsystems.estudos.spring.batch.model.LogDTO;
import com.rfsystems.estudos.spring.batch.model.LogEntity;
import com.rfsystems.estudos.spring.batch.utils.DataUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
public class LogConvertProcessor implements ItemProcessor<LogDTO, LogEntity> {


    @Override
    public LogEntity process(LogDTO logDTO) throws Exception {
        System.out.println("###########entrou processo############3");
        LocalDateTime dateTime;
        if(!logDTO.getData().isEmpty() && logDTO.getData().length() > 18){
            dateTime = DataUtils.convertFrom(logDTO.getData());
        } else {
            dateTime = null;
        }
        return LogEntity.builder()
            .data(dateTime)
            .ip(logDTO.getIp())
            .request(logDTO.getRequest())
            .status(logDTO.getStatus())
            .userAgent(logDTO.getUserAgent()).build();
    }

}

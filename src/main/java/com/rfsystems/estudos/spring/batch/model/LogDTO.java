package com.rfsystems.estudos.spring.batch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogDTO {

    private String data;

    private String ip;

    private String request;

    private int status;

    private String userAgent;

    public static LogDTO convertFrom(LogEntity log){
        return LogDTO.builder()
                .data(log.getData().toString())
                .ip(log.getIp())
                .request(log.getRequest())
                .status(log.getStatus())
                .userAgent(log.getUserAgent())
                .build();

    };

}

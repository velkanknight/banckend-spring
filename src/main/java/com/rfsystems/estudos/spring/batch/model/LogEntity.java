package com.rfsystems.estudos.spring.batch.model;


import com.rfsystems.estudos.spring.batch.utils.DataUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "Log")
@NoArgsConstructor
@AllArgsConstructor
public class LogEntity {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime data;

    private String ip;

    private String request;

    private int status;

    private String userAgent;

    public static LogEntity convertFrom(LogDTO logDTO){
        return LogEntity.builder()
                .data(DataUtils.convertFrom(logDTO.getData()))
                .ip(logDTO.getIp())
                .request(logDTO.getRequest())
                .status(logDTO.getStatus())
                .userAgent(logDTO.getUserAgent())
                .build();

    };
}

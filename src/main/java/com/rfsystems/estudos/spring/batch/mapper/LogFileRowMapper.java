package com.rfsystems.estudos.spring.batch.mapper;

import com.rfsystems.estudos.spring.batch.model.LogDTO;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class LogFileRowMapper implements FieldSetMapper<LogDTO> {

    @Override
    public LogDTO mapFieldSet(FieldSet fieldSet) {
        return LogDTO.builder()
                .data(fieldSet.readString("data"))
                .ip(fieldSet.readRawString("ip"))
                .request(fieldSet.readString("request"))
                .status(fieldSet.readInt("status"))
                .userAgent(fieldSet.readString("userAgent"))
                .build();
    }

}

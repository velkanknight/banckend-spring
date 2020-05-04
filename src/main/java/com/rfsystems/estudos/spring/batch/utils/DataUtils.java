package com.rfsystems.estudos.spring.batch.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtils {

    public static LocalDateTime convertFrom(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        if(date.contains("T")){
            String formated = date.replace("T", " ");
            return LocalDateTime.parse(formated, formatter);
        }
        if(date != null && !"null".equals(date)){
            return LocalDateTime.parse(date, formatter);
        } else {
            return null;
        }
    }

}

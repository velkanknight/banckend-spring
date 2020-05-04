package com.rfsystems.estudos.spring.batch.utils;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
class FileReaderTest {

    @Test
    void testReader() {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            String date = "2019-01-01 00:00:11.877";
//            String formatada = date.substring(0,19);
            LocalDateTime localDate = LocalDateTime.parse(date, formatter);
        System.out.println(localDate);
        }
    }



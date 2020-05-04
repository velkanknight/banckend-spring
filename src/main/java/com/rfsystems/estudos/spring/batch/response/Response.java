package com.rfsystems.estudos.spring.batch.response;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Response {

    private Set<String> validKeys = new HashSet<>();
    private Set<String> invalidKeys = new HashSet<>();

}

package com.rfsystems.estudos.spring.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    @Value("${files.path}")
    private String pathFolder;

    public void fileStorage(MultipartFile file) {

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathFolder, file.getOriginalFilename());
            Files.write(path, bytes);
            System.out.println("Arquivo salvo em: " + pathFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

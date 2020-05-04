package com.rfsystems.estudos.spring.batch.utils;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileReader {

    public static Resource[] reader(String path){
        List<Resource> resources = new ArrayList<>();
        Resource [] resources1 = new Resource[0];
        try{
            File diretorio = new File(path);
            List<File> files = Arrays.asList(diretorio.listFiles());
            files.forEach( f -> {
                resources.add( new FileSystemResource(f.getAbsoluteFile()));
            });

        } catch (Exception e) {
            System.out.println("Erro ao tentar acessar arquivo " + e);
        }

        return resources.toArray(resources1);

    }

    public static void remove(String path) {
        File diretorio = new File(path);
        List<File> files = Arrays.asList(diretorio.listFiles());
        files.forEach( f -> {
            f.delete();
        });
    }
}

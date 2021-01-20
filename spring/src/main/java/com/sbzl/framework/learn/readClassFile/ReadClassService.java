package com.sbzl.framework.learn.readClassFile;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ReadClassService {

    public void read(String path) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(path);
        File file = classPathResource.getFile();
        FileInputStream inputStream = new FileInputStream(file);
        StringBuffer buffer = new StringBuffer();
        byte[] bytes = new byte[0];
        bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        String str = new String(bytes);
        System.out.println(str);
    }
}

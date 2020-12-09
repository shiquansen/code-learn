package com.sbzl.framework.core.utils.io;

import java.io.*;

public class StreamUtil {
    public static void main(String[] args) {

    }

    private final static Integer BYTE_COUNT = 4096;

    public static FileInputStream getFileInputStream(String path) throws FileNotFoundException {
        File file = new File(path);
        if(!file.exists()){
            throw new NullPointerException("没有该文件, 路径为：" + path);
        }
        return new FileInputStream(file);
    }


    /**
     * 大文本文件缓冲流
     * @param inputStream
     * @param outputPath
     * @return
     * @throws IOException
     */
    public FileOutputStream inputToOutput(FileInputStream inputStream, String outputPath) throws IOException {

        FileOutputStream fileOutputStream = null;
        return fileOutputStream;

    }

}

package com.sbzl.framework.juc.base;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  一个简单的多线程
 */
public class ExecutorsTest implements Runnable{

    private static final Integer threadSize = 10;

    private static final Integer taskTimes = 1000;

    private static final ExecutorService executor = Executors.newFixedThreadPool(threadSize);

    private List<String> fileName;

    public ExecutorsTest(List<String> fileName) {
        this.fileName = fileName;
    }

    public ExecutorsTest() {
    }

    @Override
    public void run() {
        String parent = "C:\\Users\\k0802365\\Desktop\\temp";
        for(String file1 : fileName){
            File tempFile = new File(parent, file1 + File.separator + "txt");
            if(!tempFile.exists()){
                try {
                    tempFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void batchCreateFiles() {
        List<String> fileList = Lists.newArrayList();
        for(int i = 0; i < taskTimes; i++){
            fileList.add(UUID.randomUUID().toString());
        }
        List<List<String>> partition = Lists.partition(fileList, taskTimes/threadSize);
        for(int i = 0; i < partition.size(); i++){
            executor.execute(new ExecutorsTest(partition.get(i)));
        }
        executor.shutdown();
    }

    public static void main(String[] args) {
        batchCreateFiles();
    }
}

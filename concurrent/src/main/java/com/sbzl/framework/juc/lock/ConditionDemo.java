package com.sbzl.framework.juc.lock;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ConditionDemo extends Thread {

    private static AtomicInteger price = new AtomicInteger(0);

    private static Set copyOnWriteArraySet = new CopyOnWriteArraySet();
    @Override
    public void run() {
        copyOnWriteArraySet.add(price.addAndGet(1));
    }

    private static final Integer threadSize = 10;

    private static final Integer taskTimes = 30000;

    private static final ExecutorService executor = Executors.newFixedThreadPool(threadSize);

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i < taskTimes; i++){
             executor.execute(new ConditionDemo());
        }
        executor.shutdown();
        System.out.println("=====hashset======" + copyOnWriteArraySet.size() + "====" +UUID.randomUUID().toString());
    }
}

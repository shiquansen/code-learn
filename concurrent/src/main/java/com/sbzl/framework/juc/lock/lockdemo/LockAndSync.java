package com.sbzl.framework.juc.lock.lockdemo;

import com.sbzl.framework.juc.lock.ConditionDemo;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockAndSync extends Thread{
    private static final AtomicInteger price = new AtomicInteger(0);

    private static final CopyOnWriteArraySet<Integer> copyOnWriteArraySet = new CopyOnWriteArraySet<>();

    @Override
    public void run() {
        synchronized (price){
            price.addAndGet(1);
            System.out.println(price + "===" + Thread.currentThread());
            copyOnWriteArraySet.add(price.intValue());
        }
    }

    private static final Integer threadSize = 10;

    private static final Integer taskTimes = 50;

    private static final ExecutorService executor = Executors.newFixedThreadPool(threadSize);

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i < taskTimes; i++){
            executor.execute(new LockAndSync());
        }

        Thread.sleep(5000);
        System.out.println("size ="+copyOnWriteArraySet.size());
        System.out.println("price ="+price.intValue());
    }
}

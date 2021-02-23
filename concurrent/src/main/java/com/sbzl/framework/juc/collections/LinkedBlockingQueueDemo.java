package com.sbzl.framework.juc.collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        Queue<Integer> integerList = new LinkedBlockingQueue<>();
        CountDownLatch countDownLatch = new CountDownLatch(10000);
        for (int i = 0; i < 10000; i++){
            int finalI = i;
            Thread thread = new Thread(() -> {
                countDownLatch.countDown();
                integerList.add(finalI);
            });
            thread.start();
        }
        countDownLatch.await();
        System.out.println(integerList.size());
    }
}
class QueueDemo{
    public static void main(String[] args) throws InterruptedException {
        Queue<Integer> integerList = new LinkedList<>();
        CountDownLatch countDownLatch = new CountDownLatch(10000);
        for (int i = 0; i < 10000; i++){
            int finalI = i;
            Thread thread = new Thread(() -> {
                countDownLatch.countDown();
                integerList.add(finalI);
            });
            thread.start();
        }

        countDownLatch.await();
        System.out.println(integerList.size());
    }
}
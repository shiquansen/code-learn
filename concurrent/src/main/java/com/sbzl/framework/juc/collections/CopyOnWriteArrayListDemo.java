package com.sbzl.framework.juc.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

public class CopyOnWriteArrayListDemo {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> integerList = new CopyOnWriteArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++){
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
class ListDemo{
    public static void main(String[] args) throws InterruptedException {
        List<Integer> integerList = new ArrayList<>();
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
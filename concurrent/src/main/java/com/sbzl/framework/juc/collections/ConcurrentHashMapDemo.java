package com.sbzl.framework.juc.collections;

import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * put get
 * 难点： concurrentHashMap的扩容以及数据迁移
 */
public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        Map<Integer, Object> objectMap = new ConcurrentHashMap<>();
        CountDownLatch countDownLatch = new CountDownLatch(10000);
        for(int i = 0; i < 10000; i++){
            int finalI = i;
            Thread thread = new Thread(() -> {
                countDownLatch.countDown();
                objectMap.put(finalI, finalI);
            });
            thread.start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(objectMap.size());
    }

}
class HashMapDemo{
    public static void main(String[] args) {
        Map<Integer, Object> objectMap = new HashMap<>(1024);
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for(int i = 0; i < 1000; i++){
            int finalI = i;
            Thread thread = new Thread(() -> {
                countDownLatch.countDown();
                objectMap.put(finalI, finalI);
            });
            thread.start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(objectMap.size());
    }
}
package com.sbzl.framework.juc.lock.lockdemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
    public static void main(String[] args) throws InterruptedException {
        AtomicReference<Integer> a = new AtomicReference<>(100);
        Lock lock = new ReentrantLock();
        ExecutorService service = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 100; i++){
            service.execute(() -> {
                try{
                    lock.lock();
                    a.getAndSet(a.get() + 1);
                }finally {
                    lock.unlock();
                }

            });
        }
        Thread.sleep(1000);
        System.out.println(a);
    }
}

class SynchronizedLock{
    public static void main(String[] args) throws InterruptedException {
        AtomicReference<Integer> a = new AtomicReference<>(100);
        ExecutorService service = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 100; i++){
            service.execute(() -> {
                synchronized (a){
                    a.getAndSet(a.get() + 1);
                }
            });
        }
        Thread.sleep(1000);
        System.out.println(a);
    }
}
/*
    *   没有加锁的会导致结果跟预期不一样
 */
class NoLock{
    public static void main(String[] args) throws InterruptedException {
        AtomicReference<Integer> a = new AtomicReference<>(100);
        ExecutorService service = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 100; i++){
            service.execute(() -> {
                a.getAndSet(a.get() + 1);
            });
        }
        Thread.sleep(1000);
        System.out.println(a);
    }
}

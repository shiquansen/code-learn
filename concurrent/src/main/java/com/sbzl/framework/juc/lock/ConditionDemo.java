package com.sbzl.framework.juc.lock;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo extends Thread {

    public static void main(String[] args) {
        final ReentrantLock reentrantLock = new ReentrantLock();
        final Condition condition = reentrantLock.newCondition();

        Thread thread1 = new Thread(() -> {
           try{
             reentrantLock.lock();
             System.out.println("我要等一个新信号 thread1");
             condition.await();
             System.out.println("拿到信号 thread1");
           } catch (InterruptedException e) {
               e.printStackTrace();
           }finally {
               reentrantLock.unlock();
           }

        }, "thread1");

        Thread thread2 = new Thread(() -> {
            try{
                reentrantLock.lock();
                System.out.println("我拿到锁了 thread2");
                Thread.sleep(3000);
                condition.signal();
                System.out.println("我发了一个信号 thread2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                reentrantLock.unlock();
            }
        }, "thread2");

        thread1.start();
        thread2.start();
    }
}

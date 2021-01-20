package com.sbzl.framework.algorithmdata.leetcode;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 交替打印FooBar
 */
public class leetcode1115 {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            test();

        }
    }

    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    private Semaphore semaphore = new Semaphore(1);

    public static void test(){

        new Thread(() -> {
            if(!atomicBoolean.get()) {
                System.out.print("foo");
                atomicBoolean.set(true);
            }
           }).start();

        new Thread(() -> {
            if(atomicBoolean.get()) {
                System.out.println("bar");
                atomicBoolean.set(false);
            }
        }).start();
    }




}

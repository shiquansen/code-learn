package com.sbzl.framework.algorithmdata.leetcode.l1115;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    public static void main(String[] args) throws InterruptedException {
        SemaphoreDemo leet = new SemaphoreDemo();
        for(int i =0; i < 5; i++){
            leet.runPrint();
        }
    }

//    Semaphore fooSemaphore = new Semaphore(1);
//    Semaphore barSemaphore = new Semaphore(1);
//    CountDownLatch fooCountDownLatch = new CountDownLatch(1);


    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    public SemaphoreDemo() {
    }

    public void runPrint() throws InterruptedException {
        Semaphore barSemaphore = new Semaphore(1);
        Semaphore fooSemaphore = new Semaphore(1);
        foo(() -> {
            try {
                barSemaphore.acquire();
                System.out.print("foo");
                fooSemaphore.release();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        bar(() -> {
            try {
                fooSemaphore.acquire();
                System.out.println(" bar");
                barSemaphore.release();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        Thread.sleep(1);

        System.out.println("===========================程序结束");

    }


    public void foo(Runnable printFoo) throws InterruptedException {
        try {
            executorService.execute(printFoo);
        } catch(Exception ignored) {
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        try {
            executorService.execute(printBar);
        } catch(Exception ignored) {}
    }
}

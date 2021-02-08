package com.sbzl.framework.algorithmdata.leetcode.l1115;

import java.util.concurrent.*;

/**
 * 交替打印FooBar
 *
 * countDownLatch 等同步器要在内部使用
 */
public class CountdownlatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountdownlatchDemo leet = new CountdownlatchDemo();
        for(int i =0; i < 5; i++){
            leet.runPrint();
        }
    }

//    Semaphore fooSemaphore = new Semaphore(1);
//    Semaphore barSemaphore = new Semaphore(1);
//    CountDownLatch fooCountDownLatch = new CountDownLatch(1);


    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    public CountdownlatchDemo() {
    }

    public void runPrint() throws InterruptedException {
        CountDownLatch barCountDownLatch = new CountDownLatch(1);
        CountDownLatch fooCountDownLatch = new CountDownLatch(1);
        foo(() -> {
            try {
                fooCountDownLatch.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.print("foo");
            barCountDownLatch.countDown();
        });

        bar(() -> {
            try {
                barCountDownLatch.await();
                System.out.println(" bar");
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        fooCountDownLatch.countDown();
        Thread.sleep(1);
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

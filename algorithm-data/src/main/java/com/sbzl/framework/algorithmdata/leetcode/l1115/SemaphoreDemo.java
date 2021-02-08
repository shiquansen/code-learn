package com.sbzl.framework.algorithmdata.leetcode.l1115;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * fooSemaphore 为资源的许可，现在想要交替的打印
 * foo ——> 拿到许可 打印 给bar添加许可
 * bar ——> 拿到许可 打印 给foo添加许可
 *
 * （如果出现 bar许可初始为0，拿不到许可，等待资源）
 * （如果出现 foo foo 第二次的foo需要等bar添加资源许可）
 */
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
        Semaphore fooSemaphore = new Semaphore(0);

        bar(() -> {
            try {
                fooSemaphore.acquire();
                System.out.println(" bar");
                barSemaphore.release();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        foo(() -> {
            try {
                barSemaphore.acquire();
                System.out.print("foo");
                fooSemaphore.release();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });


        Thread.sleep(10);


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

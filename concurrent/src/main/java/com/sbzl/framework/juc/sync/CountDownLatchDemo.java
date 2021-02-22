package com.sbzl.framework.juc.sync;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo {

    /**
     * 主线程等待子线程完成才去做do something
     */
    public static void await() {
        ExecutorService service = Executors.newFixedThreadPool(11);
        final CountDownLatch latch = new CountDownLatch(11);
        for (int i = 0; i < 11; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("子线程" + Thread.currentThread().getName() + "开始执行");
                        Thread.sleep((long) (Math.random() * 1000));
                        System.out.println("子线程"+Thread.currentThread().getName()+"执行完成");
                        latch.countDown();//当前线程调用此方法，则计数减一
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }

        try {
            System.out.println("主线程"+Thread.currentThread().getName()+"等待子线程执行完成...");
            latch.await();//阻塞当前线程，直到计数器的值为0
            System.out.println("主线程"+Thread.currentThread().getName()+"开始执行...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }

    public static void main(String[] args) {
        await();
    }

    /**
     * 流程控制 所有线程同时启动（公平）
     */
//    public static void main(String[] args) {
//        ExecutorService service = Executors.newCachedThreadPool();
//        final CountDownLatch cdOrder = new CountDownLatch(1);
//        final CountDownLatch cdAnswer = new CountDownLatch(4);
//        for (int i = 0; i < 4; i++) {
//            Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        System.out.println("选手" + Thread.currentThread().getName() + "正在等待裁判发布口令");
//                        cdOrder.await();
//                        System.out.println("选手" + Thread.currentThread().getName() + "已接受裁判口令");
//                        Thread.sleep((long) (Math.random() * 10000));
//                        System.out.println("选手" + Thread.currentThread().getName() + "到达终点");
//                        cdAnswer.countDown();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            };
//            service.execute(runnable);
//        }
//        try {
//            Thread.sleep((long) (Math.random() * 10000));
//            System.out.println("裁判"+Thread.currentThread().getName()+"即将发布口令");
//            cdOrder.countDown();
//            System.out.println("裁判"+Thread.currentThread().getName()+"已发送口令，正在等待所有选手到达终点");
//            cdAnswer.await();
//            System.out.println("所有选手都到达终点");
//            System.out.println("裁判"+Thread.currentThread().getName()+"汇总成绩排名");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        service.shutdown();
//    }
}

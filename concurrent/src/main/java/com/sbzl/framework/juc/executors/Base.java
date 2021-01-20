package com.sbzl.framework.juc.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * https://segmentfault.com/a/1190000016586578
 *
 */
public class Base {

    private static final Integer threadSize = 10;

    private static final Integer taskTimes = 100;

    private static final ExecutorService executor = Executors.newFixedThreadPool(threadSize);
//    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
//    private static final ExecutorService executor = Executors.newCachedThreadPool();
//
//    private static final ExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//    private static final ExecutorService executor = Executors.newScheduledThreadPool(threadSize);

    //无顺序，抢占式执行
//    private static final ExecutorService executor = Executors.newWorkStealingPool(threadSize);


    public static void main(String[] args) {
        for(int i = 0; i < taskTimes; i++){
            executor.execute(new InnerTask());
        }
    }

    static class InnerTask implements Runnable {

        @Override
        public void run() {
            //do something
            System.out.println(Thread.currentThread().getId() + "多线程启动");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

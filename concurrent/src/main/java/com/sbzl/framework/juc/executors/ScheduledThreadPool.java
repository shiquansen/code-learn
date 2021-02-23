package com.sbzl.framework.juc.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时器
 * cron 表达式 https://www.cnblogs.com/dubhlinn/p/10740838.html
 */
public class ScheduledThreadPool {

    private static final Integer threadSize = 10;

    private static final ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(threadSize);

    /**
     *
     * https://segmentfault.com/a/1190000016672638
     * @see java.util.concurrent.ScheduledThreadPoolExecutor
     *
     * ScheduledThreadPoolExecutor比较特殊，由于要满足任务的延迟/周期调度功能，它会对所有的Runnable任务都进行包装，包装成一个RunnableScheduledFuture任务。
     *
     * int corePoolSize,
     * ThreadFactory threadFactory,
     * new DelayedWorkQueue,                内部实现的一个延时队列
     * RejectedExecutionHandler handler
     */


    /**
     * 调度
     * schedule、scheduleAtFixedRate、scheduleWithFixedDelay
     *
     *
     */
    public static void main(String[] args) {

        scheduled.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello====world");
            }
        },  3, TimeUnit.SECONDS);

        scheduled.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello====world");
            }
        },  5, 3,TimeUnit.SECONDS);
    }
}

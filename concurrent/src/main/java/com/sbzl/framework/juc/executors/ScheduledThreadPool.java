package com.sbzl.framework.juc.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScheduledThreadPool {

    private static final Integer threadSize = 10;

    private static final ExecutorService executor = Executors.newScheduledThreadPool(threadSize);

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
}

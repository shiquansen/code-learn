package com.sbzl.framework.juc.executors;

import java.util.concurrent.*;

public class FixedThreadPool {

    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    /**
     *     ThreadPoolExecutor
     *
     *     https://segmentfault.com/a/1190000016629668
     *
     *     int corePoolSize,                        核心池线程数量
     *     int maximumPoolSize,                     最大池线程数
     *     long keepAliveTime,                      存活时间
     *     TimeUnit unit,                           时间的单位
     *     BlockingQueue<Runnable> workQueue,       人物列表
     *     ThreadFactory threadFactory,             线程工厂
     *     RejectedExecutionHandler handler         拒绝策略
     */

    /**
     * 线程池状态和线程管理
     *
     *  ctl，通过按位划分的方式，在一个变量中记录线程池状态和工作线程数——低29位保存线程数，高3位保存线程池状态：
     *  @see java.util.concurrent.ThreadPoolExecutor.ctl;
     *
     *  RUNNING : 接受新任务, 且处理已经进入阻塞队列的任务
     *  SHUTDOWN : 不接受新任务, 但处理已经进入阻塞队列的任务
     *  STOP : 不接受新任务, 且不处理已经进入阻塞队列的任务, 同时中断正在运行的任务
     *  TIDYING : 所有任务都已终止, 工作线程数为0, 线程转化为TIDYING状态并准备调用terminated方法
     *  TERMINATED : terminated方法已经执行完成
     *
     * @see java.util.concurrent.ThreadPoolExecutor.Worker;
     *
     *
     * 线程工厂
     * @see java.util.concurrent.ThreadFactory
     * @see java.util.concurrent.Executors.DefaultThreadFactory
     *
     * 池工作流程
     * @see java.util.concurrent.ThreadPoolExecutor#execute
     * @see 线程池工作状态.webp
     *
     * 拒绝策略
     *
     *
     * ThreadPoolExecutor中有几个比较重要的组件：阻塞队列、核心线程池、拒绝策略，它们的关系如下图，图中的序号表示execute的执行顺序
     *
     */

}

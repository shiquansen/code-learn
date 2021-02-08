package com.sbzl.framework.algorithmdata.leetcode.l1115;


import java.util.concurrent.*;

public class cyclicbarrierdemo {

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) throws InterruptedException {
        cyclicbarrierdemo cyclicbarrierdemo = new cyclicbarrierdemo(3);
        cyclicbarrierdemo.print();
    }

    private final int n;
    private CountDownLatch a;
    private final CyclicBarrier barrier;// 使用CyclicBarrier保证任务按组执行
    public cyclicbarrierdemo(int n) {
        this.n = n;
        a = new CountDownLatch(1);
        barrier = new CyclicBarrier(2);// 保证每组内有两个任务
    }

    public void print() throws InterruptedException {
        foo(() -> System.out.print("foo"));
        bar(() -> System.out.println("bar"));
        Thread.sleep(10);
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        try {
            for (int i = 0; i < n; i++) {
                executorService.execute(printFoo);
                a.countDown();// printFoo方法完成调用countDown
                barrier.await();// 等待printBar方法执行完成
            }
        } catch(Exception e) {}
    }

    public void bar(Runnable printBar) throws InterruptedException {

        try {
            for (int i = 0; i < n; i++) {
                a.await();// 等待printFoo方法先执行
                executorService.execute(printBar);
                a = new CountDownLatch(1); // 保证下一次依旧等待printFoo方法先执行
                barrier.await();// 等待printFoo方法执行完成
            }
        } catch(Exception e) {}
    }
}
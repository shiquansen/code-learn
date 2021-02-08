package com.sbzl.framework.algorithmdata.leetcode.l1115;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 没有办法解决
 * lock的话怎么在一条线程去控制另一条线程的执行 ？？？？？
 */
public class lockdemo {

    public static void main(String[] args) throws InterruptedException {
        lockdemo leet = new lockdemo();
        for(int i =0; i < 5; i++){
            leet.runPrint();
        }
    }



    Lock lock = new ReentrantLock(true);
    volatile boolean permitFoo = true;

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    public void runPrint() throws InterruptedException {

        foo(() -> {
            try {
                lock.lock();
                if(permitFoo){
                    System.out.print("foo");
                    permitFoo = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        });

        bar(() -> {
            try {
                lock.lock();
                if(!permitFoo){
                    System.out.println(" bar");
                    permitFoo = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });




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

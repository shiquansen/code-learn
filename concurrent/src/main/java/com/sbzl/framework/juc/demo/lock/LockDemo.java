package com.sbzl.framework.juc.demo.lock;


import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;

public class LockDemo {
    // 我们往这个队列中添加字符串
    final static Queue<String> queue = new LinkedBlockingQueue<String>();
    // 创建我们自己的锁对象
    final static MyNonLock lock = new MyNonLock();
    // 当队列queue中字符串满了，其他的生产线程就丢到这个条件队列里面
    final static Condition full = lock.newCondition();
    // 当队列queue是空的，其余的消费线程就丢到这个条件队列里面
    final static Condition empty = lock.newCondition();
    // 队列queue中存字符串最多只能是3个
    final static int queue_MAX_SIZE = 3;

    //往队列queue中压入字符串
    public static void add() {
        lock.lock();
        try {
            // 当队列满了，就将其他生产线程丢进full的条件队列中
            while (queue.size() == queue_MAX_SIZE) {
                full.await();
            }
            System.out.println("prd:" + "hello");
            // 往队列queue中添加字符串
            queue.add("hello");
            // 生产成功，唤醒消费条件队列中的所有线程赶紧去消费
            empty.signalAll();
        } catch (Exception e) {
            //
        } finally {
            lock.unlock();
        }
    }

    //从队列queue弹出字符串
    public static void poll() {
        lock.lock();
        try {
            // 当队列queue中一个字符串都没有，就将剩下的消费线程丢进enpty对应的队列中
            while (queue.size() == 0) {
                empty.await();
            }
            // 消费队列queue中的字符串
            String poll = queue.poll();
            System.out.println("consumer:" + poll);
            // 消费成功，就唤醒full中所有的生产线程去生产字符串
            full.signalAll();
        } catch (Exception e) {
            //
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        // 生产者线程
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                add();
            }).start();
        }

        // 消费者线程
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                poll();
            }).start();
        }
    }
}

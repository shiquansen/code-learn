package com.sbzl.framework.juc.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger ai = new AtomicInteger();

        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Accumlator(ai), "thread-" + i);
            list.add(t);
            t.start();
        }

        for (Thread t : list) {
            t.join();
        }

        System.out.println(ai.get());
    }

    static class Accumlator implements Runnable {
        private final AtomicInteger ai;

        Accumlator(AtomicInteger ai) {
            this.ai = ai;
        }

        @Override
        public void run() {
            for (int i = 0, len = 1000; i < len; i++) {
                ai.incrementAndGet();
            }
        }
    }
}

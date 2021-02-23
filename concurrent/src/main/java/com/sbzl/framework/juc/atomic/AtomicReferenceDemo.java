package com.sbzl.framework.juc.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        AtomicReference<Integer> ref = new AtomicReference<>(1000);

        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(new Task(ref), "Thread-" + i);
            list.add(t);
            t.start();
        }

        for (Thread t : list) {
            t.join();
        }

        System.out.println(ref.get());    // 打印2000
    }

}

class Task implements Runnable {
    private AtomicReference<Integer> ref;

    Task(AtomicReference<Integer> ref) {
        this.ref = ref;
    }

    @Override
    public void run() {
        for ( ; ; ) {    //自旋操作
            Integer oldV = ref.get();
            if (ref.compareAndSet(oldV, oldV + 1))  // CAS操作
                break;
        }
    }
}
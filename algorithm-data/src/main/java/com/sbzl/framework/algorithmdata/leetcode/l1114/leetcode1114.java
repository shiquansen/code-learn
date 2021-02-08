package com.sbzl.framework.algorithmdata.leetcode.l1114;
import java.util.concurrent.CountDownLatch;


/**
 * 按序打印
 * tip: 将CountDownLatch外置并不能知道线程什么时候启动完
 *
 *
 *
 * 可能会出现
 * firstLatch.countDown
 * secondLatch.countDown
 * thirdLatch.countDown 虽然是同步的
 * 但是线程是异步的，极可能 firstLatch.countDown -> secondLatch.countDown -> secondThread -> firstThread
 *
 * 所以场景下线程锁控制顺序还是要在线程内完成
 */
public class leetcode1114 {


    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            test();
        }
    }

    public static synchronized void test() throws InterruptedException {
        CountDownLatch firstLatch = new CountDownLatch(1);
        CountDownLatch secondLatch = new CountDownLatch(1);
        CountDownLatch thirdLatch = new CountDownLatch(1);
//        InnerPrint firstPrint = new InnerPrint("first", null, secondLatch);
//        InnerPrint secondPrint = new InnerPrint("second", secondLatch, thirdLatch);
//        InnerPrint thirdPrint = new InnerPrint("third", thirdLatch, null);
//
//        new Thread(firstPrint).start();
//        new Thread(secondPrint).start();
//        new Thread(thirdPrint).start();

        new Thread(() -> {
            try {
                secondLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("second");
            thirdLatch.countDown();}).start();

        new Thread(() -> {
            try {
                firstLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("first");
            secondLatch.countDown();}).start();

        new Thread(() -> {
            try {
                thirdLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("third");}).start();

        firstLatch.countDown();

        System.out.println("================= finish ==================");

        Thread.sleep(1000);

    }





    static class InnerPrint implements Runnable{
        private String print;

        private CountDownLatch thisLatch;
        private CountDownLatch nextLatch;

        public InnerPrint(String print, CountDownLatch thisLatch, CountDownLatch nextLatch) {
            this.print = print;
            this.thisLatch = thisLatch;
            this.nextLatch = nextLatch;
        }

        @Override
        public void run() {
            if(thisLatch != null){
                thisLatch.countDown();
            }
            System.out.println("打印" + Thread.currentThread().getName() + print);
            if(nextLatch != null){
                try {
                    nextLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

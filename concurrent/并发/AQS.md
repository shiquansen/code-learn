https://segmentfault.com/a/1190000015562787

### AQS框架分离了构建同步器时的一系列关注点，它的所有操作都围绕着资源——同步状态（synchronization state）来展开因此，围绕着资源，
- 衍生出三个基本问题：
    - 同步状态（synchronization state）的管理
    - 阻塞/唤醒线程的操作
    - 线程等待队列的管理
    
    1. 同步状态
        1. 同步状态，其实就是资源。AQS使用单个int（32位）来保存同步状态，并暴露出getState、setState以及compareAndSetState操作来读取和更新这个状态。
        ```
               /**
                * 同步状态.
                */
           private volatile int state;
           
           protected final int getState() {
               return state;
           }
           
           protected final void setState(int newState) {
               state = newState;
           }
               /**
                * 以原子的方式更新同步状态.
                * 利用Unsafe类实现
                */
           protected final boolean compareAndSetState(int expect, int update) {
               return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
           }
       
        ```
    2. 线程的阻塞/唤醒
       在JDK1.5之前，除了内置的监视器机制外，没有其它方法可以安全且便捷得阻塞和唤醒当前线程。
       JDK1.5以后，java.util.concurrent.locks包提供了LockSupport类来作为线程阻塞和唤醒的工具。
   
    3 等待队列
       等待队列，是AQS框架的核心，整个框架的关键其实就是如何在并发状态下管理被阻塞的线程。
       等待队列是严格的FIFO队列，是Craig，Landin和Hagersten锁（CLH锁）的一种变种，采用双向链表实现，因此也叫CLH队列。
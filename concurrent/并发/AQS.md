https://segmentfault.com/a/1190000015562787
https://segmentfault.com/a/1190000015804888

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
       
- AQS内置node
    - 变量
        - 内置节点(static final Node SHARED = new Node(); static final Node EXCLUSIVE = null;) 共享和独占节点;
        - <table>
          <thead><tr>
          <th>结点状态</th>
          <th>值</th>
          <th>描述</th>
          </tr></thead>
          <tbody>
          <tr>
          <td>CANCELLED</td>
          <td>1</td>
          <td>取消。表示后驱结点被中断或超时，需要移出队列</td>
          </tr>
          <tr>
          <td>SIGNAL</td>
          <td>-1</td>
          <td>发信号。表示后驱结点被阻塞了（当前结点在入队后、阻塞前，应确保将其prev结点类型改为SIGNAL，以便prev结点取消或释放时将当前结点唤醒。）</td>
          </tr>
          <tr>
          <td>CONDITION</td>
          <td>-2</td>
          <td>Condition专用。表示当前结点在Condition队列中，因为等待某个条件而被阻塞了</td>
          </tr>
          <tr>
          <td>PROPAGATE</td>
          <td>-3</td>
          <td>传播。适用于共享模式（比如连续的读操作结点可以依次进入临界区，设为PROPAGATE有助于实现这种迭代操作。）</td>
          </tr>
          <tr>
          <td>INITIAL</td>
          <td>0</td>
          <td>默认。新结点会处于这种状态</td>
          </tr>
          </tbody>
          </table>
        - volatile int waitStatus;
        - volatile Node prev;
        - volatile Node next;
        - Node nextWaiter;  //Condition队列使用，存储condition队列中的后继节点
        - private transient volatile Node head;
        - private transient volatile Node tail;
        - private volatile int state;
        - static final long spinForTimeoutThreshold = 1000L;
    - 方法
        - isShared();
        - predecessor() 返回pre，如果pre没值，初始化head，返回pre
        - compareAndSetState(int expect, int update) cas
        - enq(final Node node) //cas方式插入队列，如果尾节点为null，初始化头尾节点  
            ```
             Node t = tail;
             if (t == null) { // Must initialize
                 if (compareAndSetHead(new Node()))
                   tail = head;
             node.prev = t;
                 if (compareAndSetTail(t, node)) 
                     t.next = node;
            ```
        - addWaiter(Node mode)  //插入队列，调用enq，在enq之前执行一次快速插入的操作
          ```
            Node node = new Node(Thread.currentThread(), mode);
                    // Try the fast path of enq; backup to full enq on failure
                    Node pred = tail;
                    if (pred != null) {
                        node.prev = pred;
                        if (compareAndSetTail(pred, node)) {
                            pred.next = node;
                            return node;
                        }
                    }
                    enq(node);
                    return node;
          ```
        - setHead(Node node)
        
        - unparkSuccessor(Node node)
        - doReleaseShared()
        - setHeadAndPropagate(Node node, int propagate)
        - cancelAcquire(Node node)
        - shouldParkAfterFailedAcquire(Node pred, Node node)
        - selfInterrupt()
        - parkAndCheckInterrupt()
        - acquireQueued(final Node node, int arg)
                                                                                            
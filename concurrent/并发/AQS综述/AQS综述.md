https://segmentfault.com/a/1190000015562787
https://segmentfault.com/a/1190000015804888

### AQS框架分离了构建同步器时的一系列关注点，它的所有操作都围绕着资源——同步状态（synchronization state）来展开因此，围绕着资源，
```
一、AQS简介
    1.1 提供一套模板框架
    1.2 支持中断、超时
    1.3 支持独占模式和共享模式
    1.4 支持Condition条件等待
二、AQS方法说明
    2.1 CAS操作
    2.2 等待队列的核心操作
    2.3 资源的获取操作
    2.4 资源的释放操作
三、AQS原理简述
    3.1 同步状态
    3.2 线程的阻塞/唤醒
    3.3 等待队列
四、总结
```


<article class="article fmt article-content ">
<h2 id="item-1">一、AQS简介</h2>
<p>AbstractQueuedSynchronizer抽象类（以下简称AQS）是整个<code>java.util.concurrent</code>包的核心。在JDK1.5时，<a href="http://gee.cs.oswego.edu/dl/papers/aqs.pdf">Doug Lea</a>引入了J.U.C包，该包中的大多数同步器都是基于AQS来构建的。AQS框架提供了一套通用的机制来管理同步状态（synchronization state）、阻塞/唤醒线程、管理等待队列。</p>
<p>我们所熟知的ReentrantLock、CountDownLatch、CyclicBarrier等同步器，其实都是通过内部类实现了AQS框架暴露的API，以此实现各类同步器功能。这些同步器的主要区别其实就是对同步状态（synchronization state）的定义不同。</p>
<p>AQS框架，分离了构建同步器时的一系列关注点，它的所有操作都围绕着资源——同步状态（synchronization state）来展开，并替用户解决了如下问题：</p>
<ol>
<li>资源是可以被同时访问？还是在同一时间只能被一个线程访问？（共享/独占功能）</li>
<li>访问资源的线程如何进行并发管理？（等待队列）</li>
<li>如果线程等不及资源了，如何从等待队列退出？（超时/中断）</li>
</ol>
<p>这其实是一种典型的模板方法设计模式：父类（AQS框架）定义好骨架和内部操作细节，具体规则由子类去实现。<br>AQS框架将剩下的一个问题留给用户：<br><strong><em>什么是资源？如何定义资源是否可以被访问？</em></strong></p>
<p>我们来看下几个常见的同步器对这一问题的定义：</p>
<table>
<thead><tr>
<th>同步器</th>
<th>资源的定义</th>
</tr></thead>
<tbody>
<tr>
<td>ReentrantLock</td>
<td>资源表示独占锁。State为0表示锁可用；为1表示被占用；为N表示重入的次数</td>
</tr>
<tr>
<td>CountDownLatch</td>
<td>资源表示倒数计数器。State为0表示计数器归零，所有线程都可以访问资源；为N表示计数器未归零，所有线程都需要阻塞。</td>
</tr>
<tr>
<td>Semaphore</td>
<td>资源表示信号量或者令牌。State≤0表示没有令牌可用，所有线程都需要阻塞；大于0表示由令牌可用，线程每获取一个令牌，State减1，线程没释放一个令牌，State加1。</td>
</tr>
<tr>
<td>ReentrantReadWriteLock</td>
<td>资源表示共享的读锁和独占的写锁。state逻辑上被分成两个16位的unsigned short，分别记录读锁被多少线程使用和写锁被重入的次数。</td>
</tr>
</tbody>
</table>
<p>综上所述，AQS框架提供了以下功能：</p>
<h3 id="item-1-1">1.1 提供一套模板框架</h3>
<p>由于并发的存在，需要考虑的情况非常多，因此能否以一种相对简单的方法来完成这两个目标就非常重要，因为对于用户（AQS框架的使用者来说），很多时候并不关心内部复杂的细节。而AQS其实就是利用模板方法模式来实现这一点，AQS中大多数方法都是final或是private的，也就是说Doug Lea并不希望用户直接使用这些方法，而是只覆写部分模板规定的方法。<br>AQS通过暴露以下API来让让用户自己解决上面提到的“<strong>如何定义资源是否可以被访问</strong>”的问题：</p>
<table>
<thead><tr>
<th>钩子方法</th>
<th>描述</th>
</tr></thead>
<tbody>
<tr>
<td>tryAcquire</td>
<td>排它获取（资源数）</td>
</tr>
<tr>
<td>tryRelease</td>
<td>排它释放（资源数）</td>
</tr>
<tr>
<td>tryAcquireShared</td>
<td>共享获取（资源数）</td>
</tr>
<tr>
<td>tryReleaseShared</td>
<td>共享获取（资源数）</td>
</tr>
<tr>
<td>isHeldExclusively</td>
<td>是否排它状态</td>
</tr>
</tbody>
</table>
<h3 id="item-1-2">1.2 支持中断、超时</h3>
<p>还记得Lock接口中的那些锁中断、限时等待、锁尝试的方法吗？这些方法的实现其实AQS都内置提供了。<br>使用了AQS框架的同步器，都支持下面的操作：</p>
<ul>
<li>阻塞和非阻塞（例如tryLock）同步；</li>
<li>可选的超时设置，让调用者可以放弃等待；</li>
<li>可中断的阻塞操作。</li>
</ul>
<h3 id="item-1-3">1.3 支持独占模式和共享模式</h3>
<h3 id="item-1-4">1.4 支持Condition条件等待</h3>
<p><a href="https://segmentfault.com/a/1190000015562196#articleHeader3">Condition</a>接口，可以看做是Obejct类的wait()、notify()、notifyAll()方法的替代品，与Lock配合使用。<br>AQS框架内部通过一个内部类<code>ConditionObject</code>，实现了Condition接口，以此来为子类提供条件等待的功能。</p>
<h2 id="item-2">二、AQS方法说明</h2>
<p>在本章第一部分讲到，AQS利用了模板方法模式，其中大多数方法都是final或是private的，我们把这类方法称为<strong><em>Skeleton Method</em></strong>，也就是说这些方法是AQS框架自身定义好的骨架，子类是不能覆写的。<br>下面会按类别简述一些比较重要的方法，具体实现细节及原理会在本系列后续部分详细阐述。</p>
<h3 id="item-2-5">2.1 CAS操作</h3>
<p>CAS，即CompareAndSet，在Java中CAS操作的实现都委托给一个名为UnSafe类，关于<code>Unsafe</code>类，以后会专门详细介绍该类，目前只要知道，通过该类可以实现对字段的原子操作。</p>
<table>
<thead><tr>
<th>方法名</th>
<th>修饰符</th>
<th>描述</th>
</tr></thead>
<tbody>
<tr>
<td>compareAndSetState</td>
<td>protected final</td>
<td>CAS修改同步状态值</td>
</tr>
<tr>
<td>compareAndSetHead</td>
<td>private final</td>
<td>CAS修改等待队列的头指针</td>
</tr>
<tr>
<td>compareAndSetTail</td>
<td>private final</td>
<td>CAS修改等待队列的尾指针</td>
</tr>
<tr>
<td>compareAndSetWaitStatus</td>
<td>private static final</td>
<td>CAS修改结点的等待状态</td>
</tr>
<tr>
<td>compareAndSetNext</td>
<td>private static final</td>
<td>CAS修改结点的next指针</td>
</tr>
</tbody>
</table>
<h3 id="item-2-6">2.2 等待队列的核心操作</h3>
<table>
<thead><tr>
<th>方法名</th>
<th>修饰符</th>
<th>描述</th>
</tr></thead>
<tbody>
<tr>
<td>enq</td>
<td>private</td>
<td>入队操作</td>
</tr>
<tr>
<td>addWaiter</td>
<td>private</td>
<td>入队操作</td>
</tr>
<tr>
<td>setHead</td>
<td>private</td>
<td>设置头结点</td>
</tr>
<tr>
<td>unparkSuccessor</td>
<td>private</td>
<td>唤醒后继结点</td>
</tr>
<tr>
<td>doReleaseShared</td>
<td>private</td>
<td>释放共享结点</td>
</tr>
<tr>
<td>setHeadAndPropagate</td>
<td>private</td>
<td>设置头结点并传播唤醒</td>
</tr>
</tbody>
</table>
<h3 id="item-2-7">2.3 资源的获取操作</h3>
<table>
<thead><tr>
<th>方法名</th>
<th>修饰符</th>
<th>描述</th>
</tr></thead>
<tbody>
<tr>
<td>cancelAcquire</td>
<td>private</td>
<td>取消获取资源</td>
</tr>
<tr>
<td>shouldParkAfterFailedAcquire</td>
<td>private static</td>
<td>判断是否阻塞当前调用线程</td>
</tr>
<tr>
<td>acquireQueued</td>
<td>final</td>
<td>尝试获取资源,获取失败尝试阻塞线程</td>
</tr>
<tr>
<td>doAcquireInterruptibly</td>
<td>private</td>
<td>独占地获取资源（响应中断）</td>
</tr>
<tr>
<td>doAcquireNanos</td>
<td>private</td>
<td>独占地获取资源（限时等待）</td>
</tr>
<tr>
<td>doAcquireShared</td>
<td>private</td>
<td>共享地获取资源</td>
</tr>
<tr>
<td>doAcquireSharedInterruptibly</td>
<td>private</td>
<td>共享地获取资源（响应中断）</td>
</tr>
<tr>
<td>doAcquireSharedNanos</td>
<td>private</td>
<td>共享地获取资源（限时等待）</td>
</tr>
</tbody>
</table>
<table>
<thead><tr>
<th>方法名</th>
<th>修饰符</th>
<th>描述</th>
</tr></thead>
<tbody>
<tr>
<td>acquire</td>
<td>public final</td>
<td>独占地获取资源</td>
</tr>
<tr>
<td>acquireInterruptibly</td>
<td>public final</td>
<td>独占地获取资源（响应中断）</td>
</tr>
<tr>
<td>acquireInterruptibly</td>
<td>public final</td>
<td>独占地获取资源（限时等待）</td>
</tr>
<tr>
<td>acquireShared</td>
<td>public final</td>
<td>共享地获取资源</td>
</tr>
<tr>
<td>acquireSharedInterruptibly</td>
<td>public final</td>
<td>共享地获取资源（响应中断）</td>
</tr>
<tr>
<td>tryAcquireSharedNanos</td>
<td>public final</td>
<td>共享地获取资源（限时等待）</td>
</tr>
</tbody>
</table>
<h3 id="item-2-8">2.4 资源的释放操作</h3>
<table>
<thead><tr>
<th>方法名</th>
<th>修饰符</th>
<th>描述</th>
</tr></thead>
<tbody>
<tr>
<td>release</td>
<td>public final</td>
<td>释放独占资源</td>
</tr>
<tr>
<td>releaseShared</td>
<td>public final</td>
<td>释放共享资源</td>
</tr>
</tbody>
</table>
<h2 id="item-3">三、AQS原理简述</h2>
<p>我们在第一节中讲到，AQS框架分离了构建同步器时的一系列关注点，它的所有操作都围绕着资源——同步状态（synchronization state）来展开因此，围绕着资源，衍生出三个基本问题：</p>
<ol>
<li>同步状态（synchronization state）的管理</li>
<li>阻塞/唤醒线程的操作</li>
<li>线程等待队列的管理</li>
</ol>
<h3 id="item-3-9">3.1 同步状态</h3>
<p><strong><em>同步状态的定义</em></strong><br>同步状态，其实就是资源。AQS使用单个int（32位）来保存同步状态，并暴露出getState、setState以及compareAndSetState操作来读取和更新这个状态。</p>
<div class="widget-codetool" style="display:none;">

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
</div>
<h3 id="item-3-10">3.2 线程的阻塞/唤醒</h3>
<p>在JDK1.5之前，除了内置的监视器机制外，没有其它方法可以安全且便捷得阻塞和唤醒当前线程。<br>JDK1.5以后，java.util.concurrent.locks包提供了<a href="https://segmentfault.com/a/1190000015562456">LockSupport</a>类来作为线程阻塞和唤醒的工具。</p>
<h3 id="item-3-11">3.3 等待队列</h3>
<p>等待队列，是AQS框架的核心，整个框架的关键其实就是如何在并发状态下管理被阻塞的线程。<br>等待队列是严格的FIFO队列，是Craig，Landin和Hagersten锁（CLH锁）的一种变种，采用双向链表实现，因此也叫CLH队列。</p>
<p><strong>1. 结点定义</strong><br>CLH队列中的结点是对线程的包装，结点一共有两种类型：独占（EXCLUSIVE）和共享（SHARED）。<br>每种类型的结点都有一些状态，其中独占结点使用其中的CANCELLED(1)、SIGNAL(-1)、CONDITION(-2)，共享结点使用其中的CANCELLED(1)、SIGNAL(-1)、PROPAGATE(-3)。</p>
<table>
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
<blockquote>AQS使用CLH队列实现线程的结构管理，而CLH结构正是用前一结点某一属性表示当前结点的状态，之所以这种做是因为在双向链表的结构下，这样更容易实现取消和超时功能。<br><br>next指针：用于维护队列顺序，当临界区的资源被释放时，头结点通过next指针找到队首结点。<br>prev指针：用于在结点（线程）被取消时，让当前结点的前驱直接指向当前结点的后驱完成出队动作。</blockquote>
<div class="widget-codetool" style="display:none;">
  
    // 共享模式结点
    static</span> final <span class="hljs-keyword">Node</span> <span class="hljs-title">SHARED</span> = new <span class="hljs-keyword">Node</span><span class="hljs-title">();
    
    // 独占模式结点
    static</span> final <span class="hljs-keyword">Node</span> <span class="hljs-title">EXCLUSIVE</span> = null;

    static final int CANCELLED =  <span class="hljs-number">1</span>;

    static final int SIGNAL    = -<span class="hljs-number">1</span>;

    static final int CONDITION = -<span class="hljs-number">2</span>;

    static final int PROPAGATE = -<span class="hljs-number">3</span>;

    /**
    * INITAL：      <span class="hljs-number">0</span> - 默认，新结点会处于这种状态。
    * CANCELLED：   <span class="hljs-number">1</span> - 取消，表示后续结点被中断或超时，需要移出队列；
    * SIGNAL：      -<span class="hljs-number">1</span>- 发信号，表示后续结点被阻塞了；（当前结点在入队后、阻塞前，应确保将其prev结点类型改为SIGNAL，以便prev结点取消或释放时将当前结点唤醒。）
    * CONDITION：   -<span class="hljs-number">2</span>- Condition专用，表示当前结点在Condition队列中，因为等待某个条件而被阻塞了；
    * PROPAGATE：   -<span class="hljs-number">3</span>- 传播，适用于共享模式。（比如连续的读操作结点可以依次进入临界区，设为PROPAGATE有助于实现这种迭代操作。）
    * 
    * waitStatus表示的是后续结点状态，这是因为AQS中使用CLH队列实现线程的结构管理，而CLH结构正是用前一结点某一属性表示当前结点的状态，这样更容易实现取消和超时功能。
    */
    volatile int waitStatus;

    // 前驱指针
    volatile <span class="hljs-keyword">Node</span> <span class="hljs-title">prev</span>;

    // 后驱指针
    volatile <span class="hljs-keyword">Node</span> <span class="hljs-title">next</span>;

    // 结点所包装的线程
    volatile Thread thread;

    // Condition队列使用，存储condition队列中的后继节点
    <span class="hljs-keyword">Node</span> <span class="hljs-title">nextWaiter</span>;

    <span class="hljs-keyword">Node</span><span class="hljs-title">() {
    }

    Node</span>(Thread thread, <span class="hljs-keyword">Node</span> <span class="hljs-title">mode</span>) { 
        this.nextWaiter = mode;
        this.thread = thread;
    }
}</code></pre>
<p><strong>2. 队列定义</strong><br>对于CLH队列，当线程请求资源时，如果请求不到，会将线程包装成结点，将其挂载在队列尾部。<br>CLH队列的示意图如下：</p>
<p>①初始状态，队列head和tail都指向空</p>
<p><span class="img-wrap"><img alt="F:\myproject\code-learn\concurrent\并发\img\bVbetIi.webp" title="clipboard.png"></span></p>
<p>②首个线程入队，先创建一个空的头结点，然后以自旋的方式不断尝试插入一个包含当前线程的新结点</p>
<p><span class="img-wrap"><img class="lazy" data-src="/img/bVbetIk?w=192&amp;h=263" alt="clipboard.png" title="clipboard.png"></span><br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetIm?w=523&amp;h=302" alt="clipboard.png" title="clipboard.png"></span></p>
<div class="widget-codetool" style="display:none;">
        <div class="widget-codetool--inner">
        <button type="button" class="btn btn-dark far fa-copy rounded-0 sflex-center copyCode" data-toggle="tooltip" data-placement="top" data-clipboard-text="/**
 * 以自旋的方式不断尝试插入结点至队列尾部
 *
 * @return 当前结点的前驱结点
 */
private Node enq(final Node node) {
    for (; ; ) {
        Node t = tail;
        if (t == null) { // 如果队列为空，则创建一个空的head结点
            if (compareAndSetHead(new Node()))
                tail = head;
        } else {
            node.prev = t;
            if (compareAndSetTail(t, node)) {
                t.next = node;
                return t;
            }
        }
    }
}" title="" data-original-title="复制"></button>
        </div>
        </div><pre class="hljs crmsh"><code>/**
 * 以自旋的方式不断尝试插入结点至队列尾部
 *
 * @return 当前结点的前驱结点
 */
private <span class="hljs-keyword">Node</span> <span class="hljs-title">enq</span>(final <span class="hljs-keyword">Node</span> <span class="hljs-title">node</span>) {
    for (; ; ) {
        <span class="hljs-keyword">Node</span> <span class="hljs-title">t</span> = tail;
        if (t == null) { // 如果队列为空，则创建一个空的head结点
            if (compareAndSetHead(new <span class="hljs-keyword">Node</span><span class="hljs-title">()))
                tail</span> = head;
        } else {
            node.prev = t;
            if (compareAndSetTail(t, <span class="hljs-keyword">node</span><span class="hljs-title">)) {
                t</span>.next = <span class="hljs-keyword">node</span><span class="hljs-title">;
                return</span> t;
            }
        }
    }
}</code></pre>
<h2 id="item-4">四、总结</h2>
<p>本章简要介绍了AQS的思想和原理，读者可以参考<a href="http://gee.cs.oswego.edu/dl/papers/aqs.pdf">Doug Lea</a>的论文，进一步了解AQS。<br>直接阅读AQS的源码比较漫无目的，后续章节，将从ReentrantLock、CountDownLatch的使用入手，讲解AQS的独占功能和共享功能。</p>
</article>                                                                                    
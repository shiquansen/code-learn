```
一、本章概述
二、ReentrantLock的公平策略原理
    2.1 ThreadA首先获取到锁
    2.2 ThreadB开始获取锁
    2.3 ThreadC开始获取锁
    2.4 ThreadA释放锁
    2.5 ThreadB唤醒后继续执行
    2.6 ThreadB释放锁
    2.7 ThreadC释放锁
三、ReentrantLock的非公平策略原理
四、AQS对中断的支持
五、AQS对限时等待的支持
    示例
六、总结
```


<h2 id="item-1">一、本章概述</h2>
<p>本章以<a href="https://segmentfault.com/a/1190000015562293">ReentrantLock</a>的调用为例，说明AbstractQueuedSynchronizer提供的独占功能。<br>本章结构如下：</p>
<ol>
<li>以ReentrantLock的公平策略为例，分析AbstractQueuedSynchronizer的独占功能</li>
<li>以ReentrantLock的非公平策略为例，分析AbstractQueuedSynchronizer的独占功能</li>
<li>分析AbstractQueuedSynchronizer的锁中断、限时等待等功能</li>
</ol>
<h2 id="item-2">二、ReentrantLock的公平策略原理</h2>
<p>本节对ReentrantLock公平策略的分析基于以下示例：</p>
<blockquote>假设现在有3个线程：ThreadA、ThreadB、ThreadC，一个公平的独占锁，3个线程会依次尝试去获取锁：<code>ReentrantLock lock=new ReentrantLock(true);</code>
</blockquote>
<p>线程的操作时序如下：</p>

    //ThreadA    lock
    //ThreadB    lock
    //ThreadC    lock
    //ThreadA    release
    //ThreadB    release
    //ThreadC    release
  
<h3 id="item-2-1">2.1 ThreadA首先获取到锁</h3>
<p>ThreadA首先调用ReentrantLock的<strong>lock</strong>方法，我们看下该方法的内部：
<p>最终其实调用了FairSync的lock方法：
<p><strong>acquire</strong>方法来自AQS：
<p>其中<strong>tryAcquire</strong>方法需要AQS的子类自己去实现，我们来看下ReentrantLock中的实现：
<p>可以看到，在ReentrantLock中，同步状态State的含义如下：</p>
<table>
<thead><tr>
<th>State</th>
<th>资源的定义</th>
</tr></thead>
<tbody>
<tr>
<td>0</td>
<td>表示锁可用</td>
</tr>
<tr>
<td>1</td>
<td>表示锁被占用</td>
</tr>
<tr>
<td>大于1</td>
<td>表示锁被占用，且值表示同一线程的重入次数</td>
</tr>
</tbody>
</table>
<p>ThreadA是首个获取锁的线程，所以上述方法会返回true，第一阶段结束。（ThreadA一直保持占有锁的状态）<br>此时，AQS中的等待队列还是空：<br><span class="img-wrap">
<h3 id="item-2-2">2.2 ThreadB开始获取锁</h3>
<p>终于，ThreadB要登场了，一样，ThreadB先去调用lock方法，最终调用AQS的acquire方法：

<p><strong>tryAcquire</strong>方法肯定是返回false（因为此时ThreadA占有着锁）。<br>接下来看下<strong>addWaiter</strong>方法，这个方法其实就是将当前调用线程包装成一个【独占结点】，添加到等待队列尾部。
<p>这里关键是enq方法，因为并发插入的情况存在，所以该方法设计成了自旋操作，保证结点能成功插入，具体步骤如下：<br>①当队列为空的时候，先创建一个dummy头结点；
<p>②进入下一次循环，插入队尾结点。
<p>好了，ThreadB已经被包装成结点插入队尾了，接下来会调用<strong>acquireQueued</strong>方法，这也是AQS中最重要的方法之一：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetMa?w=1313&amp;h=605" alt="clipboard.png" title="clipboard.png"></span></p>
<blockquote>在AQS中，等待队列中的线程都是阻塞的，当某个线程被唤醒时，只有该线程是首结点（线程）时，才有权去尝试获取锁。</blockquote>
<p>上述方法中，将ThreadB包装成结点插入队尾后，先判断ThreadB是否是首结点（注意不是头结点，头结点是个dummy结点），发现确实是首结点（node.predecessor==head），于是调用tryAcquire尝试获取锁，但是获取失败了（此时ThreadA占有着锁），就要判断是否需要阻塞当前线程。</p>
<p>判断是否需要阻塞线程：</p>
<p>注意，对于独占功能，只使用了3种结点状态：</p>
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
</tbody>
</table>
<p>对于在等待队列中的线程，如果要阻塞它，需要确保将来有线程可以唤醒它，AQS中通过将前驱结点的状态置为SIGNAL:-1来表示将来会唤醒当前线程，当前线程可以安心的阻塞。</p>
<p>看下图或许比较好理解：<br>①插入完ThreadB后，队列的初始状态如下：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetM7?w=427&amp;h=271" alt="clipboard.png" title="clipboard.png"></span></p>
<p>②虽然ThreadB是队首结点，但是它拿不到锁（被ThreadA占有着），所以ThreadB会阻塞，但在阻塞前需要设置下前驱的状态，以便将来可以唤醒我：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetM3?w=427&amp;h=271" alt="clipboard.png" title="clipboard.png"></span></p>
<p>至此，ThreadB的执行也暂告一段落了（安心得在等待队列中睡觉）。</p>
<blockquote>注意：补充一点，如果ThreadB在阻塞过程中被中断，其实是不会抛出异常的，只会在acquireQueued方法返回时，告诉调用者在阻塞器件有没被中断过，具体如果处理，要不要抛出异常，取决于调用者，这其实是一种延时中断机制。<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetMX?w=550&amp;h=272" alt="clipboard.png" title="clipboard.png"></span>
</blockquote>
<h3 id="item-2-3">2.3 ThreadC开始获取锁</h3>
<p>终于轮到ThreadC出场了，ThreadC的调用过程和ThreadB完全一样，同样拿不到锁，然后加入到等待队列队尾：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetNf?w=686&amp;h=267" alt="clipboard.png" title="clipboard.png"></span></p>
<p>然后，ThreadC在阻塞前需要把前驱结点的状态置为SIGNAL：-1，以确保将来可以被唤醒：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetNh?w=686&amp;h=267" alt="clipboard.png" title="clipboard.png"></span></p>
<p>至此，ThreadC的执行也暂告一段落了（安心得在等待队列中睡觉）。</p>
<h3 id="item-2-4">2.4 ThreadA释放锁</h3>
<p>ThreadA终于使用完了临界资源，要释放锁了，来看下ReentrantLock的<strong>unlock</strong>方法：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetNp?w=327&amp;h=79" alt="clipboard.png" title="clipboard.png"></span></p>
<p>unlock内部调用了AQS的release方法，传参1：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetNq?w=648&amp;h=197" alt="clipboard.png" title="clipboard.png"></span></p>
<p>尝试释放锁的操作<strong>tryRelease</strong>：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetNr?w=1078&amp;h=276" alt="clipboard.png" title="clipboard.png"></span></p>
<p>释放成功后，调用<strong>unparkSuccessor</strong>方法，唤醒队列中的首结点：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetNv?w=1169&amp;h=513" alt="clipboard.png" title="clipboard.png"></span></p>
<p>此时，队列状态为：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetNz?w=686&amp;h=263" alt="clipboard.png" title="clipboard.png"></span></p>
<h3 id="item-2-5">2.5 ThreadB唤醒后继续执行</h3>
<p>好了，队首结点（ThreadB）被唤醒了。<br>ThreadB会继续从以下位置开始执行，先返回一个中断标识，用于表示ThreadB在阻塞期间有没被中断过：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetNB?w=573&amp;h=203" alt="clipboard.png" title="clipboard.png"></span></p>
<p>然后ThreadB又开始了自旋操作，被唤醒的是队首结点，所以可以尝试tryAcquire获取锁，此时获取成功（ThreadA已经释放了锁）。<br>获取成功后会调用setHead方法，将头结点置为当前结点，并清除线程信息：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetMa?w=1313&amp;h=605" alt="clipboard.png" title="clipboard.png"></span><br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetNL?w=493&amp;h=128" alt="clipboard.png" title="clipboard.png"></span></p>
<p>最终的队列状态如下：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetNR?w=427&amp;h=263" alt="clipboard.png" title="clipboard.png"></span></p>
<h3 id="item-2-6">2.6 ThreadB释放锁</h3>
<p>ThreadB也终于使用完了临界资源，要释放锁了，过程和ThreadA释放时一样，释放成功后，会调用<strong>unparkSuccessor</strong>方法，唤醒队列中的首结点：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetNZ?w=427&amp;h=263" alt="clipboard.png" title="clipboard.png"></span></p>
<p>队首结点（ThreadC）被唤醒后，继续从原来的阻塞处向下执行，并尝试获取锁，获取成功，最终队列状态如下：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetN7?w=167&amp;h=255" alt="clipboard.png" title="clipboard.png"></span></p>
<h3 id="item-2-7">2.7 ThreadC释放锁</h3>
<p>ThreadC也终于使用完了临界资源，要释放锁了。释放成功后，调用unparkSuccessor方法，唤醒队列中的首结点：<br>此时队列中只剩下一个头结点（dummy），所以这个方法其实什么都不做。最终队列的状态就是只有一个dummy头结点。<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetNv?w=1169&amp;h=513" alt="clipboard.png" title="clipboard.png"></span></p>
<p>至此，AQS的独占功能已经差不多分析完了，剩下还有几个内容没分析：</p>
<ol>
<li>锁中断功能</li>
<li>限时等待功能</li>
<li>Conditon等待功能</li>
</ol>
<p>这些功能将在后续章节陆续分析。</p>
<h2 id="item-3">三、ReentrantLock的非公平策略原理</h2>
<p>ReenrantLock非公平策略的内部实现和公平策略没啥太大区别：<br>非公平策略和公平策略的最主要区别在于：</p>
<ol>
<li>公平锁获取锁时，会判断等待队列中是否有线程排在当前线程前面。只有没有情况下，才去获取锁，这是公平的含义。<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetOq?w=904&amp;h=572" alt="clipboard.png" title="clipboard.png"></span>
</li>
<li>非公平锁获取锁时，会立即尝试修改同步状态，失败后再调用AQS的acquire方法。    <br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetOs?w=884&amp;h=322" alt="clipboard.png" title="clipboard.png"></span><br>acquire方法会转调非公平锁自身的tryAcquire方法，其实最终是调了nofairTryAcquire方法，而该方法相对于公平锁，只是少了“队列中是否有其它线程排在当前线程前”这一判断：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetOw?w=663&amp;h=569" alt="clipboard.png" title="clipboard.png"></span>
</li>
</ol>
<h2 id="item-4">四、AQS对中断的支持</h2>
<p>还是以ReentrantLock为例，来看下AQS是如何实现锁中断和超时的。<br>我们知道ReentrantLock的<strong>lockInterruptibly</strong>方法是会响应中断的。（线程如果在阻塞过程中被中断，会抛出InterruptedException异常）</p>
<p>该方法调用了AQS的<strong>acquireInterruptibly</strong>方法：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetOA?w=725&amp;h=232" alt="clipboard.png" title="clipboard.png"></span></p>
<p>上述代码会先去尝试获取锁，如果失败，则调用doAcquireInterruptibly方法，如下：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetOB?w=587&amp;h=578" alt="clipboard.png" title="clipboard.png"></span></p>
<p>很眼熟有木有？看下和<strong>acquireQueued</strong>方法的对比，唯一的区别就是：<br>当调用线程获取锁失败，进入阻塞后，如果中途被中断，<strong>acquireQueued</strong>只是用一个标识记录线程被中断过，而<strong>doAcquireInterruptibly</strong>则是直接抛出异常。<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetOE?w=966&amp;h=640" alt="clipboard.png" title="clipboard.png"></span></p>
<h2 id="item-5">五、AQS对限时等待的支持</h2>
<p>Lock接口中有一个方法：<strong>tryLock</strong>，用于在指定的时间内尝试获取锁，获取不到就返回。<br>ReentrantLock实现了该方法，可以看到，该方法内部调用了AQS的<strong>tryAcquireNanos</strong>方法：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetOK?w=652&amp;h=90" alt="clipboard.png" title="clipboard.png"></span></p>
<p><strong>tryAcquireNanos</strong>方法是响应中断的，先尝试获取一次锁，失败则调用<strong>doAcquireNanos</strong>方法进行超时等待：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetOQ?w=679&amp;h=152" alt="clipboard.png" title="clipboard.png"></span></p>
<p>关键是<strong>doAcquireNano</strong>方法，和<strong>acquireQuqued</strong>方法类似，又是一个自旋操作，在超时前不断尝试获取锁，获取不到则阻塞（加上了等待时间的判断）。该方法内部，调用了<code>LockSupport.parkNanos</code>来超时阻塞线程：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetOV?w=1191&amp;h=745" alt="clipboard.png" title="clipboard.png"></span></p>
<p><code>LockSupport.parkNanos</code>内部其实通过Unsafe这个类来操作线程的阻塞，底层是一个native方法：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetOY?w=458&amp;h=93" alt="clipboard.png" title="clipboard.png"></span></p>
<p>如果当前线程在指定时间内获取不到锁，除了返回false外，最终还会执行<strong>cancelAcquire</strong>方法：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetO0?w=833&amp;h=788" alt="clipboard.png" title="clipboard.png"></span></p>
<hr>
<h3 id="item-5-8">示例</h3>
<p>为了便于理解还是以3个线程为例：</p>
<blockquote>假设现在有3个线程：ThreadA、ThreadB、ThreadC，一个公平的独占锁，3个线程会依次尝试去获取锁，不过此时加上了限时等待：ThreadB等待10s，ThreadA等待20s。</blockquote>
<div class="widget-codetool" style="display:none;">
        <div class="widget-codetool--inner">
        <button type="button" class="btn btn-dark far fa-copy rounded-0 sflex-center copyCode" data-toggle="tooltip" data-placement="top" data-clipboard-text="ReentrantLock lock=new ReentrantLock(true);

//ThreadA    tryLock

//ThreadB    tryLock, 10s

//ThreadC    tryLock, 20s

//ThreadA    release

//ThreadB    release

//ThreadC    release" title="" data-original-title="复制"></button>
        </div>
        </div><pre class="hljs awk"><code>ReentrantLock lock=new ReentrantLock(true);

<span class="hljs-regexp">//</span>ThreadA    tryLock

<span class="hljs-regexp">//</span>ThreadB    tryLock, <span class="hljs-number">10</span>s

<span class="hljs-regexp">//</span>ThreadC    tryLock, <span class="hljs-number">20</span>s

<span class="hljs-regexp">//</span>ThreadA    release

<span class="hljs-regexp">//</span>ThreadB    release

<span class="hljs-regexp">//</span>ThreadC    release</code></pre>
<p><strong><em>1. ThreadA首先获取到锁，ThreadB和ThreadC依次尝试去获取锁</em></strong>  <br>ThreadB和ThreadC经过两轮自旋操作后，等待队列的情况如下：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetPn?w=686&amp;h=279" alt="clipboard.png" title="clipboard.png"></span></p>
<p><strong><em>2. ThreadB先到超时时间</em></strong><br>调用了<strong>cancelAcquire</strong>方法取消操作，队列状态变成：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetPs?w=686&amp;h=279" alt="clipboard.png" title="clipboard.png"></span></p>
<p><strong><em>3. ThreadC到达超时时间</em></strong><br>调用了<strong>cancelAcquire</strong>方法取消操作，队列状态变成：<br><span class="img-wrap"><img class="lazy" data-src="/img/bVbetPK?w=686&amp;h=278" alt="clipboard.png" title="clipboard.png"></span></p>
<p>在退出<strong>cancelAcquire</strong>后，原来ThreadB和ThreadC对应的结点会被JVM垃圾回收器回收。</p>
<h2 id="item-6">六、总结</h2>
<p>本章从ReentrantLock入手，分析AQS的独占功能的内部实现细节。下一章，从CountDownLatch入手，看下AQS的共享功能如何实现。</p>
</article><div class="d-sm-flex d-none flex-wrap align-items-center"><div class="m-n1"><a href="/t/java" class="m-1 badge-tag">java</a><a href="/t/%E5%A4%9A%E7%BA%BF%E7%A8%8B" class="m-1 badge-tag">多线程</a></div></div><div class="my-4"><div id="OA_holder_3" class="ad-container" style="display:block"><div class="d-none d-lg-flex justify-content-center">
<script async="" src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>

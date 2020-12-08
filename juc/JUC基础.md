- **推荐博文 https://segmentfault.com/blog/ressmix_multithread?page=3**

- 线程原子操作
      ```
        read
        负责从主存储器（main memory）拷贝到工作存储器（working memory）
        write
        与上述相反，负责从工作存储器（working memory）拷贝到主存储器（main memory）
        use
        表示线程引用工作存储器（working memory）的值
        assign
        表示线程将值指定给工作存储器（working memory）
        lock
        表示线程取得锁定
        unlock
        表示线程解除锁定
      ```
- synchronied的本质
    ```
      线程欲进入synchronized时，会执行以下两类操作：
      
      强制写入主存储器（main memory）
      当线程欲进入synchronized时，如果该线程的工作存储器（working memory）上有未映像到主存储器的拷贝，则这些内容会强制写入主存储器（store->write），则这些计算结果就会对其它线程可见（visible）。
      
      工作存储器（working memory）的释放
      当线程欲进入synchronized时，工作存储器上的工作拷贝会被全部丢弃。之后，欲引用主存储器上的值的线程，必定会从主存储器将值拷贝到工作拷贝（read->load）。
      
      4.2 线程欲退出synchronized
      线程欲退出synchronized时，会执行以下操作：
      
      强制写入主存储器（main memory）
      当线程欲退出synchronized时，如果该线程的工作存储器（working memory）上有未映像到主存储器的拷贝，则这些内容会强制写入主存储器（store->write），则这些计算结果就会对其它线程可见（visible）。
    
      注意： 线程欲退出synchronized时，不会执行工作存储器（working memory）的释放 操作。
    ```
  
volatile的本质
    ```
        进行内存同步
        volatile只能做内存同步，不能取代synchronized关键字做线程同步。
        当线程欲引用volatile字段的值时，通常都会发生从主存储器到工作存储器的拷贝操作；相反的，将值指定给写着volatile的字段后，工作存储器的内容通常会立即映像到主存储器
        以原子（atomic）方式进行long、double的指定
    ```

- 线程转移状态
    ```
      当创建一个Thread子类或实现Runnable接口类的实例时，线程进入【初始】状态；
      调用实例的start方法后，线程进入【可执行】状态；
      系统会在某一时刻自动调度处于【可执行】状态的线程，被调度的线程会调用run方法，进入【执行中】状态；
      线程执行完run方法后，进入【结束】状态；
      处于【结束】状态的线程，在某一时刻，会被JVM垃圾回收；
      处于【执行中】状态的线程，若调用了Thread.yield方法，会回到【可执行】状态，等待再次被调度；
      处于【执行中】状态的线程，若调用了wait方法，会进入wait set并一直等待，直到被其它线程通过notify、notifyAll、interrupt方法唤醒；
      处于【执行中】状态的线程，若调用了Thread.sleep方法，会进入【Sleep】状态，无法继续向下执行。当sleep时间结束或被interrupt时，会回到【可执行状态】；
      处于【执行中】状态的线程，若遇到阻塞I/O操作，也会停止等待I/O完成，然后回到【可执行状态】；
  ```
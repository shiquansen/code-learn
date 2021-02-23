获取锁 ->  lock -> sync.lock() -> acquire(1) ->  tryAcquire(args(==1)) 
                                               acquireQueued(addWaiter(Node.EXCLUSIVE), arg(==1))
tryAcquire(args(==1)) --> (交由各个同步器实现)
acquireQueued(addWaiter(Node.EXCLUSIVE), arg(==1))
    addWaiter(Node.EXCLUSIVE)
        enq(Node)
    acquireQueued
        shouldParkAfterFailedAcquire
        
A   tryAcquire 成功，执行线程任务
B   addWaiter   将前一个节点的状态置为-1
C   addWaiter   将前一个节点的状态置为-1

释放锁 ->  unlock  ->  release ->  tryRelease(args(args==1))
    tryRelease(释放成功后)
        unparkSuccessor(唤醒后继节点，如果后继节点状态>0,说明任务被取消了，则从后（tail）往前查找)
            
疑问：为什么需要首节点Dummy？
答：  CLH队列的特点是   将当前节点的状态保存到它的前驱（前一个结点）
                                        
                                        
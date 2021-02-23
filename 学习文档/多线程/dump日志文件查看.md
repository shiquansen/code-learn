2021-02-23 14:36:32
Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.121-b13 mixed mode):

"DestroyJavaVM" #23 prio=5 os_prio=0 tid=0x000000001ae62000 nid=0x1f0c waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"运动员[2]" #13 prio=5 os_prio=0 tid=0x000000001a63d000 nid=0x111c waiting on condition [0x000000001b95e000]
   java.lang.Thread.State: WAITING (parking)
	at sun.misc.Unsafe.park(Native Method)
	- parking to wait for  <0x00000000d63db2e8> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2039)
	at java.util.concurrent.CyclicBarrier.dowait(CyclicBarrier.java:234)
	at java.util.concurrent.CyclicBarrier.await(CyclicBarrier.java:362)
	at com.sbzl.framework.juc.sync.CyclicBarrierDemo$PrepareWork.run(CyclicBarrierDemo.java:38)
	at java.lang.Thread.run(Thread.java:745)

"运动员[0]" #11 prio=5 os_prio=0 tid=0x000000001ae6b800 nid=0x363c waiting on condition [0x000000001b75e000]
   java.lang.Thread.State: WAITING (parking)
	at sun.misc.Unsafe.park(Native Method)
	- parking to wait for  <0x00000000d63db2e8> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2039)
	at java.util.concurrent.CyclicBarrier.dowait(CyclicBarrier.java:234)
	at java.util.concurrent.CyclicBarrier.await(CyclicBarrier.java:362)
	at com.sbzl.framework.juc.sync.CyclicBarrierDemo$PrepareWork.run(CyclicBarrierDemo.java:38)
	at java.lang.Thread.run(Thread.java:745)

"Service Thread" #10 daemon prio=9 os_prio=0 tid=0x000000001a540800 nid=0x35b0 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C1 CompilerThread2" #9 daemon prio=9 os_prio=2 tid=0x000000001a4ab800 nid=0x3edc waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread1" #8 daemon prio=9 os_prio=2 tid=0x000000001a4a9000 nid=0x3c5c waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread0" #7 daemon prio=9 os_prio=2 tid=0x000000001a4a8000 nid=0x3d40 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Monitor Ctrl-Break" #6 daemon prio=5 os_prio=0 tid=0x000000001a4a6000 nid=0x24d4 runnable [0x000000001a94e000]
   java.lang.Thread.State: RUNNABLE
	at java.net.SocketInputStream.socketRead0(Native Method)
	at java.net.SocketInputStream.socketRead(SocketInputStream.java:116)
	at java.net.SocketInputStream.read(SocketInputStream.java:171)
	at java.net.SocketInputStream.read(SocketInputStream.java:141)
	at sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)
	at sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)
	at sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)
	- locked <0x00000000d644fbc0> (a java.io.InputStreamReader)
	at java.io.InputStreamReader.read(InputStreamReader.java:184)
	at java.io.BufferedReader.fill(BufferedReader.java:161)
	at java.io.BufferedReader.readLine(BufferedReader.java:324)
	- locked <0x00000000d644fbc0> (a java.io.InputStreamReader)
	at java.io.BufferedReader.readLine(BufferedReader.java:389)
	at com.intellij.rt.execution.application.AppMainV2$1.run(AppMainV2.java:61)

"Attach Listener" #5 daemon prio=5 os_prio=2 tid=0x000000001a28c800 nid=0x1f00 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Signal Dispatcher" #4 daemon prio=9 os_prio=2 tid=0x0000000018875000 nid=0x2cd0 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Finalizer" #3 daemon prio=8 os_prio=1 tid=0x0000000018851800 nid=0x2838 in Object.wait() [0x000000001a1df000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	- waiting on <0x00000000d6188ec8> (a java.lang.ref.ReferenceQueue$Lock)
	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:143)
	- locked <0x00000000d6188ec8> (a java.lang.ref.ReferenceQueue$Lock)
	at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:164)
	at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:209)

"Reference Handler" #2 daemon prio=10 os_prio=2 tid=0x0000000003b56000 nid=0x2398 in Object.wait() [0x000000001a0df000]
   java.lang.Thread.State: WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	- waiting on <0x00000000d6186b68> (a java.lang.ref.Reference$Lock)
	at java.lang.Object.wait(Object.java:502)
	at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
	- locked <0x00000000d6186b68> (a java.lang.ref.Reference$Lock)
	at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)

"VM Thread" os_prio=2 tid=0x0000000018827000 nid=0x1fb4 runnable 

"GC task thread#0 (ParallelGC)" os_prio=0 tid=0x0000000003a7a000 nid=0x2e00 runnable 

"GC task thread#1 (ParallelGC)" os_prio=0 tid=0x0000000003a7c000 nid=0x594 runnable 

"GC task thread#2 (ParallelGC)" os_prio=0 tid=0x0000000003a7e800 nid=0x277c runnable 

"GC task thread#3 (ParallelGC)" os_prio=0 tid=0x0000000003a80000 nid=0x2854 runnable 

"GC task thread#4 (ParallelGC)" os_prio=0 tid=0x0000000003a82000 nid=0x2fa0 runnable 

"GC task thread#5 (ParallelGC)" os_prio=0 tid=0x0000000003a83800 nid=0x101c runnable 

"VM Periodic Task Thread" os_prio=2 tid=0x000000001a5ae000 nid=0x273c waiting on condition 

JNI global references: 16

Heap
 PSYoungGen      total 37888K, used 12456K [0x00000000d6180000, 0x00000000d8b80000, 0x0000000100000000)
  eden space 32768K, 38% used [0x00000000d6180000,0x00000000d6daa238,0x00000000d8180000)
  from space 5120K, 0% used [0x00000000d8680000,0x00000000d8680000,0x00000000d8b80000)
  to   space 5120K, 0% used [0x00000000d8180000,0x00000000d8180000,0x00000000d8680000)
 ParOldGen       total 86016K, used 0K [0x0000000082400000, 0x0000000087800000, 0x00000000d6180000)
  object space 86016K, 0% used [0x0000000082400000,0x0000000082400000,0x0000000087800000)
 Metaspace       used 3607K, capacity 4540K, committed 4864K, reserved 1056768K
  class space    used 402K, capacity 428K, committed 512K, reserved 1048576K
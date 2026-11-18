Q6 – Deadlock and Inter-Thread Communication
Compile: javac TokenCounter.java
Run:     java TokenCounter

The completed circular-buffer producer-consumer uses wait(), notifyAll(), and while-condition checks. Expected output:
Sum of tokens collected: 210 (expected 210)

The PDF also requires wallet transfers: first demonstrate a deadlock, then prevent it using a consistent lock-ordering rule. Add this as a separate experiment and explain the four Coffman deadlock conditions. Compare this design with ArrayBlockingQueue and timed tryLock().

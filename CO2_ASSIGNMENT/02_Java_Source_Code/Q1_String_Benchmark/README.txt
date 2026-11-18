Q1 – STRING, STRINGBUILDER OR STRINGBUFFER

Run:
  javac StringBenchmark.java
  java StringBenchmark

The program tests three workloads and three approaches. It performs warm-up and repeated timed runs, reports average nanoseconds and estimated intermediate string creations, and writes q1_results.csv.

Evidence/screenshots:
1. Terminal output showing all workload results.
2. Keep q1_results.csv as raw experimental evidence.
3. In your report, explain String immutability and why repeated concatenation copies an increasing amount of text.
4. Per-ticket text: StringBuilder is normally suitable when built locally by one thread.
5. Shared multithreaded log: use a synchronized/thread-safe design; StringBuffer synchronizes individual operations, but a dedicated logger or synchronized access may be a better complete design.

Alternative discussion: String.join / StringJoiner / String.format. Discuss their construction costs and use cases; do not assume they are automatically faster.

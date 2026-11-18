Q7 – DIAGNOSE, REDESIGN AND VALIDATE

Selected limitation: ArrayList removal from the front shifts all remaining elements. For a workload that repeatedly serves the next item from the front, this creates repeated O(n) shifting and O(n^2) total work.

Redesign: use ArrayDeque, which is designed for efficient operations at both ends. The benchmark runs the same number of enqueue-at-tail and dequeue-at-head operations for both structures at three sizes, repeats each measurement and writes q7_results.csv.

Run:
  javac QueueRedesignBenchmark.java
  java QueueRedesignBenchmark

Use the measured output to evaluate the redesign. Do not claim a fixed speed-up before seeing your own machine's CSV results. Discuss ArrayDeque's trade-off: no indexed access and no null elements.

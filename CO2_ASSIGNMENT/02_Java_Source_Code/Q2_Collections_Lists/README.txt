Q2 – ARRAYLIST, LINKEDLIST AND ARRAYDEQUE

Run:
  javac ListBenchmark.java
  java ListBenchmark

Writes q2_results.csv for three sizes and operation timings. This is a microbenchmark for learning, not a substitute for a professional benchmark harness. Run more than once and report environment and variation.

Important: ArrayDeque does not support indexed get(k), and it is not a List. The program times end insertion, front insertion/removal patterns where supported, middle insertion/removal for List types, indexed access, and full iteration.

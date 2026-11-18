Q7 – Design a Concurrent, Fault-Tolerant Processor
Compile: javac ResultProcessor.java
Run:     java ResultProcessor

The runnable demo creates 200 sample score files, deliberately omitting 10 and corrupting 10. It processes them using ExecutorService, Callable-style submitted tasks (submit(Callable)), Future, and ConcurrentLinkedQueue. It measures pool sizes 1, 2, 4 and 8. It writes q7_results.csv plus detailed results-pool-N.csv files.
Expected counts for this sample: 180 successes, 20 failures (10 missing and 10 corrupt) for each pool size.
The PDF's full-scale requirement is 10,000 files. Increase count to 10,000 for your final measurement and compare all four pool sizes. Verify a small hand-counted sample before scaling.

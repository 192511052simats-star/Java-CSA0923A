Q3 – Resource Safety, Debugging and Robust Code
Compile: javac SalesReportDemo.java
Run:     java SalesReportDemo

The program compares manual close() and try-with-resources for normal and injected missing-file scenarios.
Inspect manual-report.txt and safe-report.txt. The failing input intentionally includes missing-store.txt.
For the report, use the debugger to inspect resource lifetime, record the failure file, count open resources where feasible, and explain suppressed exceptions and reverse-order closing.

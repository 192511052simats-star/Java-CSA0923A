Q1 – Handling Failure in a Bank ATM Simulation
Compile: javac ATMWithdrawal.java
Run:     java ATMWithdrawal
For missing-card test: java ATMWithdrawal missing-card-reader.txt

Try inputs:
1) 123456 / 1000 (success)
2) abc / 1000 (InputMismatchException)
3) 123456 / -100 (unchecked validation exception)
4) 123456 / 6000 (insufficient balance)
5) run with missing-card-reader.txt as command-line argument (IOException)

The catch order is specific exceptions before broader categories. finally executes after success or handled failure.
For the assignment report, add the Throwable hierarchy, failure classification table, at least five traces, one uncaught stack trace, and compare exceptions with hasNextInt()/error codes.

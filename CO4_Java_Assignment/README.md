# CO4 – Programming in Java: Assignment 4

This package contains the CO4 report, Java source files for Tasks 3–6, the MySQL schema script, and the execution screenshots supplied for Tasks 3–5.

## Contents
- `01_Report/` – complete report PDF covering Tasks 1–6.
- `02_Java_Source_Code/` – Java implementations for Tasks 3, 4, 5 and 6.
- `03_SQL_Scripts/` – MySQL schema script for Task 6.
- `04_Execution_Evidence/` – screenshots supplied for Tasks 3–5.
- `05_Instructions/` – run instructions and evidence status.

## Requirements
- JDK 21 or compatible.
- Tasks 3–5 use standard Java AWT/Swing.
- Task 6 requires MySQL 8.x and MySQL Connector/J. Configure JDBC URL, username and password in `EventRegistrationApp.java` before running.

## Compile and run
From `02_Java_Source_Code/`, run:

```bash
javac LibrarySearchScreen.java && java LibrarySearchScreen
javac DrawPad.java && java DrawPad
javac Converter.java && java Converter
```

For Task 6, execute `03_SQL_Scripts/campus_events.sql` in MySQL, add the Connector/J JAR to the classpath, configure credentials, then run:

```bash
javac -cp .:/path/to/mysql-connector-j.jar EventRegistrationApp.java
java -cp .:/path/to/mysql-connector-j.jar EventRegistrationApp
```

macOS uses `:` between classpath entries.

## Evidence status
- Task 3: two screenshots supplied; the assignment asks for three window sizes, so one size remains unshown.
- Task 4: screenshot supplied showing drawing and click count. The deliberate compile-error demonstration was not supplied.
- Task 5: successful conversion screenshot supplied. An invalid-input screenshot was not supplied.
- Task 6: MySQL database/table creation was shown in text. The Java–JDBC GUI was not executed because Connector/J was not installed; normal, invalid and duplicate GUI registration evidence remains outstanding.
- Tasks 1 and 2 are design-based written tasks and do not have separate runnable GUI source files in this package.

Missing tests are not represented as completed.

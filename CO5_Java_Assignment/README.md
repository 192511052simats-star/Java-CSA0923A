# CO5 Java Assignment 5 – Execution Files

This package contains executable programs and SQL scripts for **Part B (B1–B4)** of the supplied CO5 assignment. It does not contain the final report/PDF.

## Requirements
- macOS with Java 21 (or compatible Java 17+)
- MySQL Server running locally
- MySQL Connector/J `.jar` (download from https://dev.mysql.com/downloads/connector/j/ ; choose Platform Independent ZIP and extract the JAR)
- MySQL Workbench is optional; SQL can also be run in the MySQL terminal.

## Database setup
Run `00_Database_Setup/co5_setup.sql` in MySQL Workbench or from the MySQL prompt:
```sql
SOURCE /full/path/to/00_Database_Setup/co5_setup.sql;
```
This creates database `campus_transport`, tables `bus_pass` and `users`, and a test login:
- username: `student1`
- password: `pass123`

## Configure JDBC
Open `CO5Config.java` in the task folder and set:
- `DB_URL = "jdbc:mysql://localhost:3306/campus_transport"`
- `DB_USER = "root"`
- `DB_PASSWORD = ""` if your local root account has no password, otherwise enter your MySQL root password.

Put the Connector/J JAR in the package root folder named `lib`, e.g.
`lib/mysql-connector-j-9.x.x.jar`.

Each task that uses JDBC has its own `CO5Config.java`; copy the JAR to `lib/` once.

## Compile and run (Terminal)
Open Terminal and `cd` into the extracted package folder. Example for B1:
```bash
cd B1_File_Store
javac *.java
java B1Demo
```
For JDBC tasks, use the JAR classpath (replace the JAR filename with yours):
```bash
javac -cp ".:../lib/mysql-connector-j-9.x.x.jar" *.java
java -cp ".:../lib/mysql-connector-j-9.x.x.jar" B2SecurityDemo
```
macOS uses `:` between classpath entries.

## Task folders
- `B1_File_Store`: pass CSV store, damaged-line handling, unbuffered/buffered line counting, generated file sizes and search-growth benchmark.
- `B2_Adversarial_Input`: unsafe SQL login versus PreparedStatement, plus damaged pass-file cases.
- `B3_JDBC_Batch`: Statement, PreparedStatement, batch inserts, transaction/rollback experiments, metadata checks.
- `B4_Migration_Decision`: CSV-to-MySQL migration with validation-failure report and DatabaseMetaData inspection.
- `00_Database_Setup`: SQL schema and sample login.

## Important honesty/evidence note
Run each program yourself and retain its actual output. Timing values vary by Mac, Java, database and disk load; the programs print measured values rather than assuming results. B3 stress sizes are 1,000, 10,000 and 100,000 rows. B4 imports a generated CSV file and reports invalid rows. The programs are demonstrations for a local assignment database, not production payment systems.

## Recommended execution order
1. Run database setup SQL.
2. B1 (no database needed).
3. Add Connector/J JAR to `lib/`.
4. Set credentials in each `CO5Config.java`.
5. Run B2, B3, then B4.

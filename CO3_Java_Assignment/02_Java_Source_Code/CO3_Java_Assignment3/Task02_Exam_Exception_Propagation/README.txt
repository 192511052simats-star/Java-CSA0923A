Q2 – User-Defined Exceptions and Propagation
Compile: javac StorageDemo.java
Run:     java StorageDemo
Expected key lines:
Saved answer01.pdf
Error: Could not store answer02.pdf
Cause: Disk quota exceeded

The IOException is wrapped in SubmissionStorageException and retained as its cause.
Add further custom exceptions for exam closed, unregistered student and oversized answer file in your analysis/extension. Include a propagation diagram and explain checked versus unchecked choices.

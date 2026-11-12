
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class TASK_7 {

    static AtomicInteger success = new AtomicInteger(0);
    static AtomicInteger failure = new AtomicInteger(0);

    static class ResultTask implements Callable<String> {
        String fileName;

        ResultTask(String fileName) {
            this.fileName = fileName;
        }

        public String call() throws Exception {

            File file = new File(
                "C:\\Users\\surit\\Desktop\\JAVA\\ASSIGNMENT 3",
                fileName
            );

            if (!file.exists()) {
                throw new FileNotFoundException("File missing");
            }

            try (BufferedReader br =
                    new BufferedReader(new FileReader(file))) {

                String data = br.readLine();

                if (data == null || !data.matches("\\d+")) {
                    throw new IOException("Corrupt file");
                }

                int mark = Integer.parseInt(data);

                return fileName + " Result: " +
                       (mark >= 50 ? "Pass" : "Fail");
            }
        }
    }

    public static void main(String[] args) throws Exception {

        int threads = 8;

        ExecutorService executor =
                Executors.newFixedThreadPool(threads);

        List<Future<String>> futures = new ArrayList<>();
        ConcurrentLinkedQueue<String> results =
                new ConcurrentLinkedQueue<>();

        long start = System.currentTimeMillis();

        // Submit all tasks
        for (int i = 1; i <= 10; i++) {
            String name = "student" + i + ".txt";
            futures.add(executor.submit(new ResultTask(name)));
        }

        // Collect results
        for (int i = 0; i < futures.size(); i++) {
            try {
                results.add(futures.get(i).get());
                success.incrementAndGet();
} catch (ExecutionException e) {
    failure.incrementAndGet();
    results.add("student" + (i + 1) +
                " - " + e.getCause());
}
        }

        executor.shutdown();

        long end = System.currentTimeMillis();

        for (String result : results) {
            System.out.println(result);
        }

        System.out.println("Successful Files: " + success.get());
        System.out.println("Failed Files: " + failure.get());
        System.out.println("Total Files: " +
                           (success.get() + failure.get()));
        System.out.println("Total Time: " + (end - start) + " ms");
    }
}

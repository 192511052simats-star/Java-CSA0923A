import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class ResultProcessor {
    record Result(String file, boolean success, int score, String message) {}

    static Result process(Path file) {
        try {
            if (!Files.exists(file)) return new Result(file.toString(), false, 0, "MISSING");
            String text = Files.readString(file).trim();
            if (text.isEmpty() || !text.matches("\\d{1,3}"))
                return new Result(file.toString(), false, 0, "CORRUPT");
            int score = Integer.parseInt(text);
            if (score < 0 || score > 100)
                return new Result(file.toString(), false, 0, "INVALID_SCORE");
            return new Result(file.toString(), true, score, "OK");
        } catch (IOException e) {
            return new Result(file.toString(), false, 0, "IO_ERROR: "+e.getMessage());
        }
    }

    static void createSampleFiles(Path dir, int count) throws IOException {
        Files.createDirectories(dir);
        for (int i=0;i<count;i++) {
            Path p=dir.resolve("script-"+i+".txt");
            if (i%20==0) continue;                 // intentionally missing
            if (i%20==1) Files.writeString(p,"CORRUPT-SCORE");
            else Files.writeString(p,Integer.toString((i*7)%101));
        }
    }

    static long runPool(List<Path> files, int poolSize, Path csv) throws Exception {
        ExecutorService pool=Executors.newFixedThreadPool(poolSize);
        ConcurrentLinkedQueue<Result> results=new ConcurrentLinkedQueue<>();
        List<Future<Result>> futures=new ArrayList<>();
        long start=System.nanoTime();
        try {
            for (Path p:files) futures.add(pool.submit(() -> process(p)));
            for (Future<Result> f:futures) {
                try { results.add(f.get()); }
                catch (ExecutionException e) {
                    results.add(new Result("UNKNOWN",false,0,"TASK_FAILURE: "+e.getCause()));
                }
            }
        } finally {
            pool.shutdown();
            if (!pool.awaitTermination(30,TimeUnit.SECONDS)) pool.shutdownNow();
        }
        long elapsed=System.nanoTime()-start;
        long ok=results.stream().filter(Result::success).count();
        long failed=results.size()-ok;
        try (PrintWriter out=new PrintWriter(Files.newBufferedWriter(csv))) {
            out.println("file,status,score,message");
            for (Result r:results)
                out.printf("\"%s\",%s,%d,\"%s\"%n",r.file(),r.success()?"SUCCESS":"FAILURE",r.score(),r.message());
        }
        System.out.printf("Pool %d: success=%d failure=%d total=%d time=%d ns%n",
                poolSize,ok,failed,results.size(),elapsed);
        return elapsed;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("=== Q7 CONCURRENT RESULT PROCESSOR ===");
        Path dir=Path.of("sample_scripts");
        int count=200;
        createSampleFiles(dir,count);
        List<Path> files=new ArrayList<>();
        for (int i=0;i<count;i++) files.add(dir.resolve("script-"+i+".txt"));
        try (PrintWriter out=new PrintWriter(Files.newBufferedWriter(Path.of("q7_results.csv")))) {
            out.println("pool_size,elapsed_ns");
            for (int p : new int[]{1,2,4,8}) {
                long elapsed=runPool(files,p,Path.of("results-pool-"+p+".csv"));
                out.printf("%d,%d%n",p,elapsed);
            }
        }
        System.out.println("Expected sample: 200 files; 180 valid and 20 failures (10 missing, 10 corrupt).");
        System.out.println("Raw timing data saved to q7_results.csv; detailed records saved per pool.");
    }
}

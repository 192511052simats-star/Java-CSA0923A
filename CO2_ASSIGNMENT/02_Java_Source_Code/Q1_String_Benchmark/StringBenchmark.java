import java.io.*;
import java.util.*;

public class StringBenchmark {
    static final int RUNS = 5;
    static volatile int blackhole;

    interface Builder { String build(String[] fields); }

    static String plus(String[] f) {
        String s = "";
        for (String x : f) s = s + "|" + x;
        return s;
    }
    static StringBuilder builder(String[] f) {
        StringBuilder s = new StringBuilder();
        for (String x : f) s.append('|').append(x);
        return s;
    }
    static StringBuffer buffer(String[] f) {
        StringBuffer s = new StringBuffer();
        for (String x : f) s.append('|').append(x);
        return s;
    }

    static String[] fields(int n, int seed) {
        String[] a = new String[n];
        for (int i = 0; i < n; i++) a[i] = "field" + seed + "_" + i;
        return a;
    }

    static long time(Builder b, String[][] tickets) {
        long start = System.nanoTime();
        int sum = 0;
        for (String[] t : tickets) sum += b.build(t).length();
        blackhole = sum;
        return System.nanoTime() - start;
    }

    static void runWorkload(String label, int ticketCount, int fieldCount,
                            PrintWriter csv) {
        String[][] data = new String[ticketCount][];
        for (int i = 0; i < ticketCount; i++) data[i] = fields(fieldCount, i);

        Builder[] methods = {
            StringBenchmark::plus,
            a -> builder(a).toString(),
            a -> buffer(a).toString()
        };
        String[] names = {"StringPlus", "StringBuilder", "StringBuffer"};

        // Warm-up each approach on a bounded subset.
        for (Builder m : methods) {
            for (int w = 0; w < 2; w++) time(m, Arrays.copyOf(data, Math.min(200, data.length)));
        }

        System.out.println("\nWorkload: " + label + " (" + ticketCount
                + " tickets, " + fieldCount + " fields/ticket)");
        for (int k = 0; k < methods.length; k++) {
            long total = 0;
            for (int r = 0; r < RUNS; r++) total += time(methods[k], data);
            double avg = total / (double) RUNS;
            // Estimate only: repeated immutable concatenation creates a new result
            // String per concatenation step; exact JVM allocations are implementation-dependent.
            long estimated = names[k].equals("StringPlus")
                    ? (long) ticketCount * fieldCount
                    : (long) ticketCount;
            System.out.printf(Locale.US, "%s average: %.0f ns; estimated intermediate/result strings: %d%n",
                    names[k], avg, estimated);
            csv.printf(Locale.US, "%s,%d,%d,%s,%d,%.0f,%d%n",
                    label, ticketCount, fieldCount, names[k], RUNS, avg, estimated);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("=== Q1 STRING BUILDING EXPERIMENT ===");
        System.out.println("Runs per approach: " + RUNS + " (after warm-up)");
        try (PrintWriter csv = new PrintWriter(new FileWriter("q1_results.csv"))) {
            csv.println("workload,tickets,fields,approach,repetitions,average_ns,estimated_strings");
            runWorkload("ShortTicket", 1, 6, csv);
            runWorkload("HighVolume", 10000, 50, csv);
            runWorkload("CustomWorkload", 3000, 100, csv);
        }
        System.out.println("\nRaw data saved to q1_results.csv");
    }
}

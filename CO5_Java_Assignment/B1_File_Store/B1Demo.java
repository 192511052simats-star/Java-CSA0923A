import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class B1Demo {
    public static void main(String[] args) throws Exception {
        Path f = Files.createTempFile("co5-passes", ".csv");
        PassStore store = new PassStore(f);
        store.add("P1001", "192311001", "Arun", 12, 4500);
        Files.writeString(f, "P1002,DAMAGED LINE\n", StandardOpenOption.APPEND);
        store.add("P1003", "192311003", "Divya", 7, 3800);
        System.out.println("Find P1003: " + Arrays.toString(store.find("P1003")));
        System.out.println("Find damaged P1002: " + Arrays.toString(store.find("P1002")));
        System.out.println("Find missing P9999: " + Arrays.toString(store.find("P9999")));
        System.out.println("Route 7 records: " + store.listByRoute(7).size());

        Path bench = Files.createTempFile("co5-log", ".txt");
        for (int mb : new int[]{1, 10, 50}) {
            String chunk = "attendance-record-123456789\n";
            long target = mb * 1024L * 1024L;
            try (var out = Files.newOutputStream(bench)) {
                byte[] bytes = chunk.getBytes(StandardCharsets.UTF_8);
                long written = 0;
                while (written < target) {
                    int n = (int)Math.min(bytes.length, target - written);
                    out.write(bytes, 0, n); written += n;
                }
            }
            long u = LineCounter.timed(() -> LineCounter.unbuffered(bench));
            long b = LineCounter.timed(() -> LineCounter.buffered(bench));
            System.out.printf("%d MB | unbuffered %.2f ms | buffered %.2f ms%n",
                    mb, u / 1e6, b / 1e6);
        }

        Path growth = Files.createTempFile("co5-growth", ".csv");
        for (int n : new int[]{1000, 10000, 100000}) {
            try (var w = Files.newBufferedWriter(growth)) {
                for (int i = 0; i < n; i++)
                    w.write("P" + i + ",REG" + i + ",Student" + i + ",1,100.0\n");
            }
            PassStore ps = new PassStore(growth);
            long start = System.nanoTime();
            ps.find("P" + (n - 1));
            long elapsed = System.nanoTime() - start;
            System.out.printf("Find last of %,d records: %.3f ms%n", n, elapsed / 1e6);
        }
        Files.deleteIfExists(f); Files.deleteIfExists(bench); Files.deleteIfExists(growth);
    }
}

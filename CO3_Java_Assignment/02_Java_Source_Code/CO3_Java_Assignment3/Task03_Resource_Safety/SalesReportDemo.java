import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SalesReportDemo {
    static void manualReport(List<Path> inputs, Path output) {
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(Files.newBufferedWriter(output));
            for (Path file : inputs) {
                System.out.println("Reading: " + file);
                reader = Files.newBufferedReader(file);
                String line;
                while ((line = reader.readLine()) != null) writer.println(line);
                reader.close();
                reader = null;
            }
            System.out.println("Manual report written: " + output);
        } catch (IOException e) {
            System.out.println("Manual version failed: " + e.getMessage());
        } finally {
            try { if (reader != null) reader.close(); } catch (IOException e) {
                System.out.println("Reader close error: " + e.getMessage());
            }
            if (writer != null) writer.close();
        }
    }

    static void safeReport(List<Path> inputs, Path output) {
        try (BufferedWriter writer = Files.newBufferedWriter(output)) {
            for (Path file : inputs) {
                System.out.println("Reading safely: " + file);
                try (BufferedReader reader = Files.newBufferedReader(file)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
            System.out.println("Try-with-resources report written: " + output);
        } catch (IOException e) {
            System.out.println("Safe version failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        List<Path> good = List.of(Path.of("store1.txt"), Path.of("store2.txt"));
        List<Path> failing = List.of(Path.of("store1.txt"), Path.of("missing-store.txt"), Path.of("store2.txt"));
        System.out.println("=== Q3 RESOURCE SAFETY ===");
        manualReport(good, Path.of("manual-report.txt"));
        safeReport(good, Path.of("safe-report.txt"));
        System.out.println("--- Injected failure ---");
        manualReport(failing, Path.of("manual-failed-report.txt"));
        safeReport(failing, Path.of("safe-failed-report.txt"));
        System.out.println("Compare report contents and resource-closing behaviour.");
    }
}

import java.io.*;
import java.nio.file.*;

public class LineCounter {
    public static long unbuffered(Path p) throws IOException {
        long lines = 0; boolean any = false; int last = -1;
        try (InputStream in = new FileInputStream(p.toFile())) {
            int b;
            while ((b = in.read()) != -1) {
                any = true; last = b;
                if (b == '\n') lines++;
            }
        }
        if (any && last != '\n') lines++;
        return lines;
    }

    public static long buffered(Path p) throws IOException {
        long lines = 0; boolean any = false; int last = -1;
        try (InputStream in = new BufferedInputStream(new FileInputStream(p.toFile()))) {
            int b;
            while ((b = in.read()) != -1) {
                any = true; last = b;
                if (b == '\n') lines++;
            }
        }
        if (any && last != '\n') lines++;
        return lines;
    }

    public static long timed(RunnableIO action) throws Exception {
        long start = System.nanoTime(); action.run(); return System.nanoTime() - start;
    }
    @FunctionalInterface public interface RunnableIO { void run() throws Exception; }
}

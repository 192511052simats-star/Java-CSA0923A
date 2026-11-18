import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PassStore {
    private final Path file;
    public PassStore(Path file) { this.file = file; }

    public void add(String passId, String regNo, String name, int route, double amount)
            throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(file,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            // CSV-safe: quote fields and escape embedded quotes.
            w.write(String.join(",", csv(passId), csv(regNo), csv(name),
                    csv(String.valueOf(route)), csv(String.valueOf(amount))));
            w.newLine();
        }
    }

    private static String csv(String s) {
        return "\"" + s.replace("\"", "\"\"") + "\"";
    }

    private static String[] parseCsv(String line) {
        List<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean quoted = false;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '"') {
                if (quoted && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    cur.append('"'); i++;
                } else quoted = !quoted;
            } else if (ch == ',' && !quoted) {
                out.add(cur.toString()); cur.setLength(0);
            } else cur.append(ch);
        }
        if (quoted) return null;
        out.add(cur.toString());
        return out.toArray(new String[0]);
    }

    public String[] find(String passId) throws IOException {
        if (!Files.exists(file)) return null;
        try (BufferedReader r = Files.newBufferedReader(file)) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] fields = parseCsv(line);
                if (fields == null || fields.length != 5) continue;
                try {
                    Integer.parseInt(fields[3]);
                    Double.parseDouble(fields[4]);
                } catch (NumberFormatException ex) { continue; }
                if (fields[0].equals(passId)) return fields;
            }
        }
        return null;
    }

    public List<String[]> listByRoute(int route) throws IOException {
        List<String[]> result = new ArrayList<>();
        if (!Files.exists(file)) return result;
        try (BufferedReader r = Files.newBufferedReader(file)) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] f = parseCsv(line);
                if (f == null || f.length != 5) continue;
                try {
                    if (Integer.parseInt(f[3]) == route) result.add(f);
                    Double.parseDouble(f[4]);
                } catch (NumberFormatException ignored) { }
            }
        }
        return result;
    }
}

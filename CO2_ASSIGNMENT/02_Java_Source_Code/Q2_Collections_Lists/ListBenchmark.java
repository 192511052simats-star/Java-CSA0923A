import java.io.*;
import java.util.*;

public class ListBenchmark {
    static volatile long sink;
    static long measure(Runnable r) {
        long s = System.nanoTime(); r.run(); return System.nanoTime() - s;
    }
    static void row(PrintWriter out, int n, String structure, String op, long ns) {
        System.out.printf("%-12s %-22s %d ns%n", structure, op, ns);
        out.printf("%d,%s,%s,%d%n", n, structure, op, ns);
    }
    public static void main(String[] args) throws Exception {
        int[] sizes = {1000, 5000, 10000};
        System.out.println("=== Q2 LIST COLLECTION EXPERIMENT ===");
        try (PrintWriter out = new PrintWriter(new FileWriter("q2_results.csv"))) {
            out.println("size,structure,operation,time_ns");
            for (int n : sizes) {
                System.out.println("\nPlaylist size: " + n);
                ArrayList<Integer> a = new ArrayList<>();
                LinkedList<Integer> l = new LinkedList<>();
                ArrayDeque<Integer> d = new ArrayDeque<>();
                for (int i=0;i<n;i++) { a.add(i); l.add(i); d.addLast(i); }

                row(out,n,"ArrayList","add-end",measure(() -> a.add(n)));
                row(out,n,"LinkedList","add-end",measure(() -> l.add(n)));
                row(out,n,"ArrayDeque","add-end",measure(() -> d.addLast(n)));

                row(out,n,"ArrayList","insert-front",measure(() -> a.add(0,-1)));
                row(out,n,"LinkedList","insert-front",measure(() -> l.addFirst(-1)));
                row(out,n,"ArrayDeque","insert-front",measure(() -> d.addFirst(-1)));

                int mid=n/2;
                row(out,n,"ArrayList","insert-middle",measure(() -> a.add(mid,-2)));
                row(out,n,"LinkedList","insert-middle",measure(() -> l.add(mid,-2)));
                // ArrayDeque has no indexed middle insertion; record as unsupported.
                row(out,n,"ArrayDeque","insert-middle-unsupported",0);

                row(out,n,"ArrayList","get-middle",measure(() -> sink += a.get(mid)));
                row(out,n,"LinkedList","get-middle",measure(() -> sink += l.get(mid)));
                row(out,n,"ArrayDeque","indexed-get-unsupported",0);

                row(out,n,"ArrayList","iterate",measure(() -> { long s=0; for(int x:a)s+=x; sink=s; }));
                row(out,n,"LinkedList","iterate",measure(() -> { long s=0; for(int x:l)s+=x; sink=s; }));
                row(out,n,"ArrayDeque","iterate",measure(() -> { long s=0; for(int x:d)s+=x; sink=s; }));

                row(out,n,"ArrayList","remove-front",measure(() -> a.remove(0)));
                row(out,n,"LinkedList","remove-front",measure(() -> l.removeFirst()));
                row(out,n,"ArrayDeque","remove-front",measure(() -> d.removeFirst()));
                row(out,n,"ArrayList","remove-middle",measure(() -> a.remove(a.size()/2)));
                row(out,n,"LinkedList","remove-middle",measure(() -> l.remove(l.size()/2)));
            }
        }
        System.out.println("\nRaw data saved to q2_results.csv");
        System.out.println("Note: single-operation timings are noisy; repeat the full program and compare trends.");
    }
}

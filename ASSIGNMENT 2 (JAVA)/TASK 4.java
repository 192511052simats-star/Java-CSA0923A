
import java.util.*;

class TASK_4 {

    static void test(int n) {

        HashMap<Integer, Integer> hash =
            new HashMap<Integer, Integer>();

        TreeMap<Integer, Integer> tree =
            new TreeMap<Integer, Integer>();

        LinkedHashMap<Integer, Integer> linked =
            new LinkedHashMap<Integer, Integer>();

        // Insert medicines
        for (int i = n; i >= 1; i--) {
            hash.put(i, i * 10);
            tree.put(i, i * 10);
            linked.put(i, i * 10);
        }

        System.out.println("\nMEDICINE COUNT: " + n);

        // 100000 lookups
        long start = System.nanoTime();

        for (int i = 0; i < 100000; i++) {
            hash.get((i % n) + 1);
        }

        System.out.println("HashMap Lookup: "
            + (System.nanoTime() - start) + " ns");

        start = System.nanoTime();

        for (int i = 0; i < 100000; i++) {
            tree.get((i % n) + 1);
        }

        System.out.println("TreeMap Lookup: "
            + (System.nanoTime() - start) + " ns");

        start = System.nanoTime();

        for (int i = 0; i < 100000; i++) {
            linked.get((i % n) + 1);
        }

        System.out.println("LinkedHashMap Lookup: "
            + (System.nanoTime() - start) + " ns");

        // Code order report
        System.out.println("Code Order Report:");

        start = System.nanoTime();

        for (Map.Entry<Integer, Integer> e : tree.entrySet()) {
            // Print medicine code and stock
        }

        System.out.println("TreeMap Report Time: "
            + (System.nanoTime() - start) + " ns");

        // Insertion order report
        System.out.println("Insertion Order Report:");

        start = System.nanoTime();

        for (Map.Entry<Integer, Integer> e : linked.entrySet()) {
            // Print medicine code and stock
        }

        System.out.println("LinkedHashMap Report Time: "
            + (System.nanoTime() - start) + " ns");

        // HashMap needs sorting for code order
        start = System.nanoTime();

        ArrayList<Integer> codes =
            new ArrayList<Integer>(hash.keySet());

        Collections.sort(codes);

        for (Integer code : codes) {
            // Print medicine code and stock
        }

        System.out.println("HashMap Sorted Report Time: "
            + (System.nanoTime() - start) + " ns");
    }

    public static void main(String[] args) {

        test(100);
        test(1000);
        test(10000);
    }
}

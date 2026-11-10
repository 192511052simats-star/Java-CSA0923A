
import java.util.*;

class Medicine {
    String code;
    String name;
    int quantity;

    Medicine(String code, String name, int quantity) {
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }
}

class TASK_7 {

    static volatile long result = 0;

    // Original: HashMap + sorting for every report
    static long hashMapReport(HashMap<String, Medicine> map) {
        ArrayList<String> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);

        long sum = 0;

        for (String key : keys) {
            Medicine m = map.get(key);
            sum += m.quantity + m.code.length();
        }

        return sum;
    }

    // Redesigned: TreeMap already keeps keys sorted
    static long treeMapReport(TreeMap<String, Medicine> map) {
        long sum = 0;

        for (Map.Entry<String, Medicine> entry : map.entrySet()) {
            Medicine m = entry.getValue();
            sum += m.quantity + m.code.length();
        }

        return sum;
    }

    public static void main(String[] args) {

        int[] sizes = {100, 1000, 10000};
        int reports = 100;

        System.out.println("HASHMAP vs TREEMAP REPORT PERFORMANCE");
        System.out.println("Reports per test: " + reports);

        for (int n : sizes) {

            HashMap<String, Medicine> hashMap = new HashMap<>();
            TreeMap<String, Medicine> treeMap = new TreeMap<>();

            // Same data inserted into both structures
            for (int i = n; i >= 1; i--) {
                String code = String.format("MED%06d", i);
                Medicine m = new Medicine(
                    code, "Medicine" + i, i * 10
                );

                hashMap.put(code, m);
                treeMap.put(code, m);
            }

            // Warm-up
            for (int i = 0; i < 10; i++) {
                result = hashMapReport(hashMap);
                result = treeMapReport(treeMap);
            }

            // Measure HashMap report time
            long start1 = System.nanoTime();

            for (int i = 0; i < reports; i++) {
                result = hashMapReport(hashMap);
            }

            long hashTime = System.nanoTime() - start1;

            // Measure TreeMap report time
            long start2 = System.nanoTime();

            for (int i = 0; i < reports; i++) {
                result = treeMapReport(treeMap);
            }

            long treeTime = System.nanoTime() - start2;

            System.out.println("\nNumber of medicines: " + n);
            System.out.println("HashMap report time: "
                    + hashTime / 1_000_000.0 + " ms");

            System.out.println("TreeMap report time: "
                    + treeTime / 1_000_000.0 + " ms");
        }
    }
}

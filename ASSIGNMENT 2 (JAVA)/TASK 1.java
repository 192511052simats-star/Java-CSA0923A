
public class Main {

    static String[] createFields(int n) {
        String[] fields = new String[n];

        for (int i = 0; i < n; i++) {
            fields[i] = "Field" + (i + 1);
        }

        return fields;
    }

    static long usingString(int tickets, String[] fields) {
        long total = 0;

        for (int i = 0; i < tickets; i++) {
            String s = "";

            for (int j = 0; j < fields.length; j++) {
                s = s + fields[j] + "|";
            }

            total += s.length();
        }
        return total;
    }

    static long usingBuilder(int tickets, String[] fields) {
        long total = 0;

        for (int i = 0; i < tickets; i++) {
            StringBuilder s = new StringBuilder();

            for (int j = 0; j < fields.length; j++) {
                s.append(fields[j]).append("|");
            }

            total += s.length();
        }
        return total;
    }

    static long usingBuffer(int tickets, String[] fields) {
        long total = 0;

        for (int i = 0; i < tickets; i++) {
            StringBuffer s = new StringBuffer();

            for (int j = 0; j < fields.length; j++) {
                s.append(fields[j]).append("|");
            }

            total += s.length();
        }
        return total;
    }

    static void test(String name, int tickets, int count) {
        String[] fields = createFields(count);
        int repeat = 5;
        long start, time;

        usingString(tickets, fields);
        usingBuilder(tickets, fields);
        usingBuffer(tickets, fields);

        System.out.println("\n" + name);

        start = System.nanoTime();
        for (int i = 0; i < repeat; i++)
            usingString(tickets, fields);
        time = (System.nanoTime() - start) / repeat;
        System.out.println("String: " + time + " ns");

        start = System.nanoTime();
        for (int i = 0; i < repeat; i++)
            usingBuilder(tickets, fields);
        time = (System.nanoTime() - start) / repeat;
        System.out.println("StringBuilder: " + time + " ns");

        start = System.nanoTime();
        for (int i = 0; i < repeat; i++)
            usingBuffer(tickets, fields);
        time = (System.nanoTime() - start) / repeat;
        System.out.println("StringBuffer: " + time + " ns");
    }

    public static void main(String[] args) {

        test("WORKLOAD 1: Short Ticket", 1, 6);

        test("WORKLOAD 2: Heavy Load", 10000, 50);

        test("WORKLOAD 3: Large Ticket", 1000, 500);
    }
}

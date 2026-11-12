java TASK_3
import java.io.*;

class TASK_3 {

    // Manual close version
    static void manualReport() {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader("store1.txt"));
            writer = new BufferedWriter(new FileWriter("manual_report.txt"));

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }

            reader.close();
            writer.close();

            System.out.println("Manual report generated");

        } catch (IOException e) {
            System.out.println("Manual Error: " + e.getMessage());

        } finally {
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IOException e) {
                System.out.println("Close Error: " + e.getMessage());
            }
        }
    }

    // Try-with-resources version
    static void safeReport() {
        try (
            BufferedReader reader =
                new BufferedReader(new FileReader("store1.txt"));
            BufferedWriter writer =
                new BufferedWriter(new FileWriter("safe_report.txt"))
        ) {
            String line;

            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }

            System.out.println("Safe report generated");

        } catch (IOException e) {
            System.out.println("Safe Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        manualReport();
        safeReport();
        System.out.println("Program Completed");
    }
}

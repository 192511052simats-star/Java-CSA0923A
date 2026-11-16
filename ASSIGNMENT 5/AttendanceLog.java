import java.io.*;

public class AttendanceLog {

    public static void main(String[] args) {

        String file = "attendance.txt";
        int lines = 0;

        // Buffered reading
        try (BufferedReader br = new BufferedReader(
                new FileReader(file))) {

            while (br.readLine() != null) {
                lines++;
            }

            System.out.println("Total Lines: " + lines);

        } catch (IOException e) {
            System.out.println("File reading error: " + e.getMessage());
        }
    }
}
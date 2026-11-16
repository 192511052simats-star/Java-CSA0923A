
import java.io.*;
import java.util.Scanner;

public class BusPass {

    static final String FILE = "buspasses.txt";
    static Scanner sc = new Scanner(System.in);

    // Add a new bus pass
    static void addPass() {
        try {
            BufferedWriter bw = new BufferedWriter(
                new FileWriter(FILE, true));

            System.out.print("Enter Pass ID: ");
            String id = sc.nextLine();

            System.out.print("Enter Register Number: ");
            String reg = sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine().replace("|", "\\|");

            System.out.print("Enter Route Number: ");
            String route = sc.nextLine();

            System.out.print("Enter Boarding Stop: ");
            String stop = sc.nextLine().replace("|", "\\|");

            System.out.print("Enter Validity Date: ");
            String date = sc.nextLine();

            System.out.print("Enter Amount Paid: ");
            String amount = sc.nextLine();

            bw.write(id + "|" + reg + "|" + name + "|" +
                     route + "|" + stop + "|" + date + "|" + amount);

            bw.newLine();
            bw.close();

            System.out.println("Pass added successfully!");

        } catch (IOException e) {
            System.out.println("File writing error!");
        }
    }

    // Find pass by ID
    static void findPass() {
        try {
            System.out.print("Enter Pass ID to search: ");
            String searchID = sc.nextLine();

            BufferedReader br = new BufferedReader(
                new FileReader(FILE));

            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|", -1);

                if (data.length == 7 &&
                    data[0].equals(searchID)) {

                    System.out.println("Pass Found!");
                    System.out.println(line);
                    found = true;
                    break;
                }
            }

            br.close();

            if (!found) {
                System.out.println("Pass not found!");
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("File reading error!");
        }
    }

    // List passes by route
    static void listByRoute() {
        try {
            System.out.print("Enter Route Number: ");
            String searchRoute = sc.nextLine();

            BufferedReader br = new BufferedReader(
                new FileReader(FILE));

            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|", -1);

                if (data.length == 7 &&
                    data[3].equals(searchRoute)) {

                    System.out.println(line);
                    found = true;
                }
            }

            br.close();

            if (!found) {
                System.out.println("No passes found!");
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("File reading error!");
        }
    }

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n--- BUS PASS MANAGEMENT ---");
            System.out.println("1. Add New Pass");
            System.out.println("2. Find Pass by ID");
            System.out.println("3. List Passes by Route");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    addPass();
                    break;

                case "2":
                    findPass();
                    break;

                case "3":
                    listByRoute();
                    break;

                case "4":
                    System.out.println("Program Ended.");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}

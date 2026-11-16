
import java.util.Scanner;

public class A5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int hostel = 2000;
        int canteen = 1000;

        System.out.print("Enter transfer amount: ");
        int amount = sc.nextInt();

        if (amount > 0 && amount <= hostel) {
            hostel -= amount;
            canteen += amount;

            System.out.println("Transfer Successful");
        } else {
            System.out.println("Transfer Failed");
        }

        System.out.println("Hostel Balance: " + hostel);
        System.out.println("Canteen Balance: " + canteen);

        sc.close();
    }
}


import java.util.Scanner;

class TASK_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int balance = 5000;

        try {
            System.out.print("Enter Account Number: ");
            int account = sc.nextInt();

            System.out.print("Enter Withdrawal Amount: ");
            int amount = sc.nextInt();

            if (amount <= 0) {
                throw new IllegalArgumentException("Negative or zero amount not allowed");
            }

            if (amount > balance) {
                throw new ArithmeticException("Insufficient Balance");
            }

            System.out.println("Withdrawal Successful");
            System.out.println("Remaining Balance: " + (balance - amount));

        } catch (java.util.InputMismatchException | IllegalArgumentException e) {
            System.out.println("Invalid Input: " + e.getMessage());

        } catch (ArithmeticException e) {
            System.out.println("Transaction Failed: " + e.getMessage());

        } finally {
            System.out.println("ATM Session Closed");
            sc.close();
        }
    }
}

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ATMWithdrawal {
    private static final double BALANCE = 5000.00;

    static void checkCardReader(Path file) throws IOException {
        if (!Files.exists(file)) throw new IOException("Card-reader file is missing: " + file);
    }

    static void withdraw(int account, double amount) {
        if (account <= 0) throw new IllegalArgumentException("Account number must be positive.");
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive.");
        if (amount > BALANCE) throw new IllegalArgumentException("Insufficient balance.");
        if (amount % 100 != 0) throw new IllegalArgumentException("Amount must be in multiples of 100.");
        System.out.printf("Withdrawal successful. Account %d, amount Rs. %.2f%n", account, amount);
    }

    public static void main(String[] args) {
        System.out.println("=== Q1 ATM WITHDRAWAL ===");
        Path cardFile = Path.of(args.length > 0 ? args[0] : "card-reader.txt");
        try {
            checkCardReader(cardFile);
            try (Scanner sc = new Scanner(System.in)) {
                System.out.print("Enter account number: ");
                int account = sc.nextInt();
                System.out.print("Enter withdrawal amount: ");
                double amount = sc.nextDouble();
                withdraw(account, amount);
            }
        } catch (InputMismatchException e) {
            System.out.println("Input error: Enter numeric values only.");
        } catch (IOException e) {
            System.out.println("Card reader error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Transaction rejected: " + e.getMessage());
        } finally {
            System.out.println("ATM session closed (finally executed).");
        }
    }
}

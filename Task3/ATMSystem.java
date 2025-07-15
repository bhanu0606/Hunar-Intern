
import java.util.HashMap;
import java.util.Scanner;
public class ATMSystem {
    // Account class
    static class Account {
        private String accountNumber;
        private String pin;
        private double balance;
        public Account(String accountNumber, String pin, double balance) {
            this.accountNumber = accountNumber;
            this.pin = pin;
            this.balance = balance;
        }
        public boolean validatePin(String inputPin) {
            return this.pin.equals(inputPin);
        }
        public double getBalance() {
            return balance;
        }
        public void deposit(double amount) {
            if (amount > 0) {
                balance += amount;
            }
        }
        public boolean withdraw(double amount) {
            if (amount <= balance) {
                balance -= amount;
                return true;
            }
            return false;
        }
    }
    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashMap<String, Account> accounts = new HashMap<>();
        // Pre-loaded sample accounts
        accounts.put("1001", new Account("1001", "1234", 5000.0));
        accounts.put("1002", new Account("1002", "4321", 3000.0));
        System.out.println("=== Welcome to the ATM ===");
        System.out.print("Enter Account Number: ");
        String accNumber = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();
        Account account = accounts.get(accNumber);
        if (account != null && account.validatePin(pin)) {
            System.out.println("Login successful.");
            int choice;
            do {
                System.out.println("\n--- ATM MENU ---");
                System.out.println("1. Balance Inquiry");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Exit");
                System.out.print("Choose an option (1-4): ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input. Enter a number (1-4): ");
                    scanner.next();
                }
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Your balance is ₹" + account.getBalance());
                        break;
                    case 2:
                        System.out.print("Enter amount to deposit: ₹");
                        double depositAmount = getValidAmount(scanner);
                        account.deposit(depositAmount);
                        System.out.println("Deposit successful! Updated balance: ₹" + account.getBalance());
                        break;
                    case 3:
                        System.out.print("Enter amount to withdraw: ₹");
                        double withdrawAmount = getValidAmount(scanner);
                        if (account.withdraw(withdrawAmount)) {
                            System.out.println("Withdrawal successful! Updated balance: ₹" + account.getBalance());
                        } else {
                            System.out.println("Insufficient funds! Available balance: ₹" + account.getBalance());
                        }
                        break;
                    case 4:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please choose between 1 and 4.");
                }
            } while (choice != 4);
        } else {
            System.out.println("Authentication failed. Please check your account number and PIN.");
        }
        scanner.close();
    }
    // Helper method to validate deposit/withdraw amount
    private static double getValidAmount(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid amount. Please enter a valid number: ₹");
            scanner.next();
        }
        double amount = scanner.nextDouble();
        if (amount <= 0) {
            System.out.print("Amount must be positive. Try again: ₹");
            return getValidAmount(scanner);
        }
        return amount;
    }
}

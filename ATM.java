import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;

class Transaction {
    private Date date;
    private String type;
    private double amount;
    public Transaction(Date date, String type, double amount) {
        this.date = date;
        this.type = type;
        this.amount = amount;
    }
    public String toString() {
        return "Date: " + date + ", Type: " + type + ", Amount: $" + amount;
    }
}
class Account {
    private String username;
    private String password;
    private double balance;
    private ArrayList<Transaction> transactionHistory;
    public Account(String username, String password, double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }
    public String getUsername() {
        return username;
    }
    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }
    public double getBalance() {
        return balance;
    }
    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction(new Date(), "Deposit", amount));
        System.out.println("Deposited: $" + amount);
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add(new Transaction(new Date(), "Withdrawal", amount));
            System.out.println("Withdrawn: $" + amount);
            return true;
        } else {
            System.out.println("Insufficient balance");
            return false;
        }
    }
    public boolean transfer(Account recipient, double amount) {
        if (withdraw(amount)) {
            recipient.deposit(amount);
            transactionHistory.add(new Transaction(new Date(), "Transfer to " + recipient.getUsername(), amount));
            return true;
        } else {
            return false;
        }
    }
    public void displayTransactionHistory() {
        System.out.println("Transaction History for " + getUsername() + ":");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}
public class ATM {
    private Map<String, Account> accounts;
    private Account currentAccount;
    private Scanner scanner;
    public ATM() {
        accounts = new HashMap<>();
        // Initialize accounts (for demonstration purposes)
        accounts.put("@pavansingh", new Account("@pavansingh", "pavan#212", 1000));
        accounts.put("user2", new Account("user2", "password2", 2000));
        scanner = new Scanner(System.in);
    }
    public void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Account account = accounts.get(username);
        if (account != null && account.verifyPassword(password)) {
            currentAccount = account;
            System.out.println("Login successful");
        } else {
            System.out.println("Invalid username or password");
        }
    }
    public void showBalance() {
        if (currentAccount != null) {
            System.out.println("Balance: $" + currentAccount.getBalance());
        } else {
            System.out.println("Please login first");
        }
    }
    public void deposit() {
        if (currentAccount != null) {
            System.out.print("Enter amount to deposit: $");
            double amount = scanner.nextDouble();
            currentAccount.deposit(amount);
        } else {
            System.out.println("Please login first");
        }
    }
    public void withdraw() {
        if (currentAccount != null) {
            System.out.print("Enter amount to withdraw: $");
            double amount = scanner.nextDouble();
            currentAccount.withdraw(amount);
        } else {
            System.out.println("Please login first");
        }
    }

    public void transfer() {
        if (currentAccount != null) {
            System.out.print("Enter recipient username: ");
            String recipientUsername = scanner.next();
            Account recipient = accounts.get(recipientUsername);
            if (recipient != null) {
                System.out.print("Enter amount to transfer: $");
                double amount = scanner.nextDouble();
                currentAccount.transfer(recipient, amount);
            } else {
                System.out.println("Recipient account not found");
            }
        } else {
            System.out.println("Please login first");
        }
    }
    public void displayTransactionHistory() {
        if (currentAccount != null) {
            currentAccount.displayTransactionHistory();
        } else {
            System.out.println("Please login first");
        }
    }
    public void logout() {
        currentAccount = null;
        System.out.println("Logged out successfully");
    }
    public static void main(String[] args) {
        ATM atm = new ATM();
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;

        while (!quit) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Login");
            System.out.println("2. Show Balance");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer");
            System.out.println("6. Transaction History");
            System.out.println("7. Logout");
            System.out.println("8. Quit");
            System.out.println("9. Add Account");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    atm.login();
                    break;
                case 2:
                    atm.showBalance();
                    break;
                case 3:
                    atm.deposit();
                    break;
                case 4:
                    atm.withdraw();
                    break;
                case 5:
                    atm.transfer();
                    break;
                case 6:
                    atm.displayTransactionHistory();
                    break;
                case 7:
                    atm.logout();
                    break;
                case 8:
                    quit = true;
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                case 9:
                    atm.addAccount();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
    private void addAccount() {
    }
}


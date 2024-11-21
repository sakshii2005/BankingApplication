import java.util.ArrayList;
import java.util.Scanner;

// Base Account Class
abstract class Account {
    private int accountNumber;
    private String accountHolderName;
    protected double balance;

    // Constructor
    public Account(int accountNumber, String accountHolderName, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
    }

    // Getters
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    // Abstract method for deposit and withdraw
    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);

    // Display account details
    public void displayAccountDetails() {
        System.out.println("Account Number: " + accountNumber + ", Holder: " + accountHolderName + ", Balance: " + balance);
    }
}

// Savings Account Class
class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(int accountNumber, String accountHolderName, double initialDeposit, double interestRate) {
        super(accountNumber, accountHolderName, initialDeposit);
        this.interestRate = interestRate;
    }

    // Calculate and add interest to the balance
    public void addInterest() {
        double interest = balance * (interestRate / 100);
        balance += interest;
        System.out.println("Interest of " + interest + " added. New balance: " + balance);
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited " + amount + ". New balance: " + balance);
    }

    @Override
    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            balance -= amount;
            System.out.println("Withdrawn " + amount + ". New balance: " + balance);
        }
    }
}

// Current Account Class
class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount(int accountNumber, String accountHolderName, double initialDeposit, double overdraftLimit) {
        super(accountNumber, accountHolderName, initialDeposit);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited " + amount + ". New balance: " + balance);
    }

    @Override
    public void withdraw(double amount) {
        if (balance - amount < -overdraftLimit) {
            System.out.println("Overdraft limit exceeded!");
        } else {
            balance -= amount;
            System.out.println("Withdrawn " + amount + ". New balance: " + balance);
        }
    }
}

// Bank Manager Class
class BankManager {
    private ArrayList<Account> accounts = new ArrayList<>();

    // Create a new account
    public void createAccount(Account account) {
        accounts.add(account);
        System.out.println("Account created successfully!");
    }

    // Find an account by account number
    private Account findAccount(int accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    // Deposit to an account
    public void deposit(int accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
        } else {
            System.out.println("Account not found!");
        }
    }

    // Withdraw from an account
    public void withdraw(int accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.withdraw(amount);
        } else {
            System.out.println("Account not found!");
        }
    }

    // Display account details
    public void displayAccountDetails(int accountNumber) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.displayAccountDetails();
        } else {
            System.out.println("Account not found!");
        }
    }

    // Display all accounts
    public void displayAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found!");
        } else {
            System.out.println("All Accounts:");
            for (Account account : accounts) {
                account.displayAccountDetails();
            }
        }
    }
}

// Main Class
public class BankingApplication {
    public static void main(String[] args) {
        BankManager manager = new BankManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nBanking Application:");
            System.out.println("1. Create Savings Account");
            System.out.println("2. Create Current Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. View Account Details");
            System.out.println("6. View All Accounts");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Account Number: ");
                    int savingsAccNum = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Account Holder Name: ");
                    String savingsName = scanner.nextLine();
                    System.out.print("Enter Initial Deposit: ");
                    double savingsDeposit = scanner.nextDouble();
                    System.out.print("Enter Interest Rate: ");
                    double interestRate = scanner.nextDouble();
                    manager.createAccount(new SavingsAccount(savingsAccNum, savingsName, savingsDeposit, interestRate));
                    break;

                case 2:
                    System.out.print("Enter Account Number: ");
                    int currentAccNum = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Account Holder Name: ");
                    String currentName = scanner.nextLine();
                    System.out.print("Enter Initial Deposit: ");
                    double currentDeposit = scanner.nextDouble();
                    System.out.print("Enter Overdraft Limit: ");
                    double overdraftLimit = scanner.nextDouble();
                    manager.createAccount(new CurrentAccount(currentAccNum, currentName, currentDeposit, overdraftLimit));
                    break;

                case 3:
                    System.out.print("Enter Account Number: ");
                    int depositAccNum = scanner.nextInt();
                    System.out.print("Enter Amount to Deposit: ");
                    double depositAmount = scanner.nextDouble();
                    manager.deposit(depositAccNum, depositAmount);
                    break;

                case 4:
                    System.out.print("Enter Account Number: ");
                    int withdrawAccNum = scanner.nextInt();
                    System.out.print("Enter Amount to Withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    manager.withdraw(withdrawAccNum, withdrawAmount);
                    break;

                case 5:
                    System.out.print("Enter Account Number: ");
                    int viewAccNum = scanner.nextInt();
                    manager.displayAccountDetails(viewAccNum);
                    break;

                case 6:
                    manager.displayAllAccounts();
                    break;

                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}


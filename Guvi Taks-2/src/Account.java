/*  1.3) Create Class Account with data member As Balance. Create two constructors (no argument, and two arguments) and perform following task

a. method to deposit the amount to the account.

b. method to withdraw the amount from the account.

c. method to display the Balance    */

class Account {
    // Data member
    private double balance;

    // No-argument constructor - initializes balance to 0
    public Account() {
        this.balance = 0.0;
    }

    // Parameterized constructor - initializes balance with the given value
    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    // Method to deposit the amount to the account
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }

    // Method to withdraw the amount from the account
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
        } else if (amount > balance) {
            System.out.println("Insufficient balance");
        } else {
            System.out.println("Invalid withdrawal amount");
        }
    }

    // Method to display the balance
    public void displayBalance() {
        System.out.println("Current Balance: " + balance);
    }

    // Main method to test the Account class
    public static void main(String[] args) {
        // Creating an Account object using the no-argument constructor
        Account account1 = new Account();

        System.out.println("ACCOUNT 1");
        account1.displayBalance();
        // Depositing and withdrawing from account1
        account1.deposit(1000);
        account1.withdraw(500);
        account1.displayBalance();

        System.out.println(" ");

        System.out.println("ACCOUNT 2");
        // Creating an Account object using the parameterized constructor
        Account account2 = new Account(2000);
        account2.displayBalance();

        // Depositing and withdrawing from account2
        account2.deposit(1500);
        account2.withdraw(1000);
        account2.displayBalance();
    }
}

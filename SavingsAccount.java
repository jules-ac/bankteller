package lab4;

public class SavingsAccount implements IBankAccount {

    private double balance;
    private String username;
    private String password;
    private String accountType = "Savings";

    public SavingsAccount(String username, String password, double amount) {
        this.username = username;
        this.password = password;
        balance = amount;
    }

    public void deposit(double amount) {
        balance += amount;
    }
    public void withdrawal(double amount) throws InsufficientFundsException {
        if (amount>balance) {
            throw new InsufficientFundsException();
        }
        else {
            balance -= amount;
        }
    }
    public double checkBalance() {
        return balance;
    }
    public void monthlyProcessing() {
        if (balance>500.00) {
            balance += (balance*.03);
        }
    }
    public String getAccountType() {
        return accountType;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String toDataString() {
        return username + " " + password + " " + accountType + " " + balance + "\n";
    }
}

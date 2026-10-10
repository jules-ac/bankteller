package lab4;

public class SavingsAccount implements IBankAccount {

    private double balance;
    private String username;
    private String password;
    private String accountType = "Savings";

    public SavingsAccount(String username, String password, double amount) {
        this.username = username;
        this.password = password;
        balance = Math.floor(amount*100)/100;
    }

    public void deposit(double amount) {
        balance += Math.floor(amount*100)/100;
    }
    public void withdrawal(double amount) throws InsufficientFundsException {
        if (amount>balance) {
            throw new InsufficientFundsException();
        }
        else {
            balance -= Math.floor(amount*100)/100;
        }
    }
    public double checkBalance() {
        return balance;
    }
    public void monthlyProcessing() {
            balance += (balance*.03);
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

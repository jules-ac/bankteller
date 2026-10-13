// Julia Conti
// CSC 102
// 10/13/2026

package lab4;

/**
 * creates a savings account with a username, password, and balance,
 * and provides relevant accessors & mutator methods
 */
public class SavingsAccount implements IBankAccount {

    private double balance;
    private String username;
    private String password;
    private String accountType = "Savings";

    /**
     * constructs a savings account
     * @param username account username
     * @param password account password
     * @param amount initial account balance
     */
    public SavingsAccount(String username, String password, double amount) {
        this.username = username;
        this.password = password;
        balance = Math.floor(amount*100)/100;
    }

    /**
     * adds an amount to the account balance
     * @param amount amount to add to balance
     */
    public void deposit(double amount) {
        balance += Math.floor(amount*100)/100;
    }

    /**
     * removes an amount from the account balance
     * @param amount amount to remove from balance
     * @throws InsufficientFundsException if amount provided exceeds balance
     */
    public void withdrawal(double amount) throws InsufficientFundsException {
        if (amount>balance) {
            throw new InsufficientFundsException();
        }
        else {
            balance -= Math.floor(amount*100)/100;
        }
    }

    /**
     * returns account balance
     * @return account balance
     */
    public double checkBalance() {
        return balance;
    }

    /**
     * applies 3% monthly bonus to balance
     */
    public void monthlyProcessing() {
            balance += (balance*.03);
    }

    /**
     * returns account type ( Savings )
     * @return account type
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * returns account username
     * @return account username
     */
    public String getUsername() {
        return username;
    }

    /**
     * returns account password
     * @return account password
     */
    public String getPassword() {
        return password;
    }

    /**
     * places account information into a single string
     * @return string of account information
     */
    public String toDataString() {
        return username + " " + password + " " + accountType + " " + balance;
    }
}

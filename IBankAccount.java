// Julia Conti
// CSC 102
// 10/13/2026

package lab4;

public interface IBankAccount {
    void deposit(double amount);
    void withdrawal(double amount) throws InsufficientFundsException;
    double checkBalance();
    void monthlyProcessing();
    String getAccountType();
    String getUsername();
    String getPassword();
    String toDataString();
}

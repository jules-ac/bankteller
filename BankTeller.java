package lab4;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
public class BankTeller {


    public static int accountIndex(String username, ArrayList<IBankAccount> arr) {
        int index = 0;
        for (int i=0;i<arr.size();i++) {
            if ((arr.get(i)).getUsername().equals(username)) {
                index = i;
            }
        }
        return index;
    }
    public static boolean usernameExists(String username, ArrayList<IBankAccount> arr) {
        for (IBankAccount acct : arr) {
            if (acct.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean passwordMatches(String username, String password, ArrayList<IBankAccount> arr) {
        for (int i=0;i<arr.size();i++) {
            if ((arr.get(i)).getUsername().equals(username)) {
                if ((arr.get(i)).getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static void addAccount(Scanner scan, ArrayList<IBankAccount> arr) {

        String username = scan.next();
        String password = scan.next();
        String accountType = scan.next();
        double balance = scan.nextDouble();

        if (accountType.equals("Checking")) {
            arr.add(new CheckingAccount(username, password, balance));
        }
        else if (accountType.equals("Savings")) {
            arr.add(new SavingsAccount(username, password, balance));
        }
    }
    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<IBankAccount> accounts = new ArrayList<IBankAccount>();

        File records = new File("records.txt");
        Scanner accScan = new Scanner(records);
        Scanner userScan = new Scanner(System.in);

        while (accScan.hasNextLine()) {
            addAccount(accScan, accounts);
        }

        System.out.println(" . - = Juliaville Bank = - . ");

        System.out.print("Login\nUsername: ");
        String username = userScan.next();

        if (!usernameExists(username, accounts)) {
            System.out.println("Account with specified username does not exist.");
        }

        System.out.print("\nPassword: ");
        String password = userScan.next();

        if (!passwordMatches(username, password, accounts)) {
            System.out.println("Incorrect password.");
        }

        System.out.println("\nChoose an Action");
        System.out.print("1 - View balance\n2 - Deposit amount\n3 - Withdraw amount");
        int choice = userScan.nextInt();

        int userIndex = accountIndex(username, accounts);

        switch(choice) {
            case 1:
                System.out.println((accounts.get(userIndex)).checkBalance());
                break;
            case 2:
                System.out.print("Enter amount to deposit: ");
                accounts.get(userIndex).deposit(userScan.nextDouble());
                break;
            case 3:
                System.out.print("Enter amount to withdraw: ");
                accounts.get(userIndex).withdrawal(userScan.nextDouble());
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

}

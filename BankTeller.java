package lab4;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

/**
 * allows users to log into a bank account and
 * withdraw from, deposit to, or view its balance
 */
public class BankTeller {

    /**
     * returns index value of the provided username if it exists
     * inside the provided arrayList of bank accounts.
     * otherwise, returns 0
     * @param username username to search for
     * @param arr arrayList to search within
     * @return index value
     */
    public static int getUserIndex(String username, ArrayList<IBankAccount> arr) {
        int index = 0;
        for (int i=0;i<arr.size();i++) {
            if ((arr.get(i)).getUsername().equals(username)) {
                index = i;
            }
        }
        return index;
    }

    /**
     * checks if provided username exists in provided arrayList of bank accounts
     * @param username username to search for
     * @param arr arrayList to search within
     * @return true if exists
     */
    public static boolean usernameExists(String username, ArrayList<IBankAccount> arr) {
        for (IBankAccount acct : arr) {
            if (acct.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks if provided username and password are associated with the same
     * bank account in the provided arrayList of bank accounts
     * @param username username to search for
     * @param password password to check against username
     * @param arr arrayList to search within
     * @return true if associated
     */
    public static boolean passwordMatches(String username, String password, ArrayList<IBankAccount> arr) {
        for (IBankAccount acc : arr) {
            if (acc.getUsername().equals(username)) {
                if (acc.getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * creates new bank account(s) from a file and places them
     * inside a provided arrayList while mindful of account type
     * @param scan file scanner
     * @param arr arrayList to store bank accounts in
     */
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

    /**
     * allows user to log in to bank account
     * @param scan user input scanner
     * @param arr arrayList of bank accounts to search during login
     * @return username if successful, 0 if unsuccessful
     */
    public static String login(Scanner scan, ArrayList<IBankAccount> arr) {
        String username = "";
        String password = "";
        while (true) {
            System.out.println("\nLogin (enter 0 to quit)\n");
            System.out.print("Username: ");
            username = scan.next();
            if (username.equals("0")) {
                break;
            }
            if (!usernameExists(username, arr)) {
                System.out.println("Account with specified username does not exist.");
                continue;
            }
            int chances = 3;
            while (true) {
                System.out.print("Password: ");
                password = scan.next();
                if (!passwordMatches(username, password, arr)) {
                    System.out.println("\nPassword incorrect.");
                    if (chances>1) {
                        chances--;
                    } else {
                        System.out.println("\nToo many incorrect guesses. Restarting login.");
                        break;
                    }
                } else { return username; }
            }
        }
        return username;
    }

    /**
     * allows user to view, deposit to and withdraw from a bank account
     * @param scan user input scanner
     * @param currUser account username
     * @param arr arrayList of bank accounts to search for username within
     */
    public static void teller(Scanner scan, String currUser, ArrayList<IBankAccount> arr) {
        int userIndex = getUserIndex(currUser, arr);
        while (true) {
            System.out.println("\nChoose an Action");
            System.out.print("1 - View balance\n2 - Deposit amount\n3 - Withdraw amount\n0 - Quit\n--> ");
            if (scan.hasNextInt()) {
                int choice = scan.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("\n" +(arr.get(userIndex)).checkBalance());
                        break;
                    case 2:
                        System.out.print("\nEnter amount to deposit: ");
                        arr.get(userIndex).deposit(scan.nextDouble());
                        break;
                    case 3:
                        System.out.print("\nEnter amount to withdraw: ");
                        arr.get(userIndex).withdrawal(scan.nextDouble());
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("\nInvalid choice");
                }
            }
            else {
                System.out.println("\nInvalid choice format");
                scan.next();
            }
        }
    }

    /**
     * place updated account data back into the original file
     * @param writer file writer
     * @param arr arrayList of bank accounts to read data from
     * @throws IOException if file could not be accessed
     */
    public static void updateFile(FileWriter writer, ArrayList<IBankAccount> arr) throws IOException {
        for (int i=0;i<arr.size();i++) {
            writer.write(arr.get(i).toDataString());
            if (i!=arr.size()-1) {
                writer.write("\n");
            }
        }
    }

    /**
     * main
     * @throws FileNotFoundException if scanner fails to access file
     */
    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<IBankAccount> accounts = new ArrayList<IBankAccount>();

        File records = new File("records.txt");
        Scanner accScan = new Scanner(records);
        Scanner userScan = new Scanner(System.in);

        while (accScan.hasNextLine()) {
            addAccount(accScan, accounts);
        }

        System.out.println("^_^ Juliaville Bank ^_^");

        String currentUser = login(userScan, accounts);

        if (!currentUser.equals("0")) {
            teller(userScan, currentUser, accounts);
        }
        try {
            FileWriter writer = new FileWriter("records.txt");
            updateFile(writer, accounts);
            writer.close();
        }
        catch (IOException e) {
            System.out.println("Output file could not be accessed");
        }
    }
}

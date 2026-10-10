package lab4;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
public class BankTeller {


    public static int getUserIndex(String username, ArrayList<IBankAccount> arr) {
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
                        continue;
                    } else {
                        System.out.println("\nToo many incorrect guesses. Restarting login.");
                        break;
                    }
                } else { return username; }
            }
        }
        return username;
    }

    public static void teller(Scanner scan, String currUser, ArrayList<IBankAccount> arr) {

        int userIndex = getUserIndex(currUser, arr);
        while (true) {
            System.out.println("\nChoose an Action");
            System.out.print("1 - View balance\n2 - Deposit amount\n3 - Withdraw amount\n0 - Quit\n--> ");
            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    System.out.println((arr.get(userIndex)).checkBalance());
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ");
                    arr.get(userIndex).deposit(scan.nextDouble());
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    arr.get(userIndex).withdrawal(scan.nextDouble());
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public static void updateFile(FileWriter writer, ArrayList<IBankAccount> arr) throws IOException {

        for (int i=0;i<arr.size();i++) {
            writer.write(arr.get(i).getUsername() + " " + arr.get(i).getPassword() + " " + arr.get(i).getAccountType() + " " + arr.get(i).checkBalance());
            if (i!=arr.size()-1) {
                writer.write("\n");
            }
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

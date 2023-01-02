import java.util.Scanner;

public class PersonalAccount {
    private static PersonalAccount[] accounts = new PersonalAccount[100];
    private static int numAccounts;

    private String id;
    private double balance;

    public PersonalAccount(String id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter the balance: ");
        double balance = scanner.nextDouble();

        createAccount(id, balance);
        printAccounts();
        goBack();
    }

    private static void createAccount(String id, double balance) {
        PersonalAccount account = new PersonalAccount(id, balance);
        accounts[numAccounts] = account;
        numAccounts++;
    }
    private static void printAccounts() {
        for (int i = 0; i < numAccounts; i++) {
            PersonalAccount account = accounts[i];
            System.out.println(account.getId() + ": " + account.getBalance());
        }
    }
    private static void goBack() {
        Menu.main(new String[0]);
    }

    public static int getNumAccounts() {
        return numAccounts;
    }

    public static PersonalAccount[] getAccounts() {
        return accounts;
    }
    public String getId() {
        return id;
    }
    public double getBalance() {
        return balance;
    }
    public static void viewAccount(String id) {
        for (int i = 0; i < numAccounts; i++) {
            PersonalAccount account = accounts[i];
            if (account.getId().equals(id)) {
                System.out.println("ID: " + account.getId());
                System.out.println("Balance: " + account.getBalance());
            }
        }
    }

}

import java.util.Scanner;

public class BusinessAccount {
    private static BusinessAccount[] accounts = new BusinessAccount[100];
    private static int numAccounts;

    private String id;
    private double balance;
    private String type;

    public BusinessAccount(String id, double balance, String type) {
        this.id = id;
        this.balance = balance;
        this.type = type;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the ID: ");
        String id = scanner.nextLine();

        if (idExists(id)) {
            System.out.println("This ID is already in use.");
            main(new String[0]);
        }

        System.out.print("Enter the balance: ");
        double balance = scanner.nextDouble();

        String type = BusinessType.inputType();
        BusinessType.checkType(type);

        createAccount(id, balance, type);
        printAccounts();
        goBack();
    }

    private static boolean idExists(String id) {
        for (int i = 0; i < numAccounts; i++) {
            BusinessAccount account = accounts[i];
            if (account.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private static void createAccount(String id, double balance, String type) {
        BusinessAccount account = new BusinessAccount(id, balance, type);
        accounts[numAccounts] = account;
        numAccounts++;
    }

    private static void printAccounts() {
        for (int i = 0; i < numAccounts; i++) {
            BusinessAccount account = accounts[i];
            System.out.println(account.getId() + ": " + account.getBalance() + ": " + account.getType());
        }
    }

    private static void goBack() {
        Menu.main(new String[0]);
    }

    public static int getNumAccounts() {
        return numAccounts;
    }

    public static BusinessAccount[] getAccounts() {
        return accounts;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public String getType() {
        return type;
    }
    public static void viewAccount(String id) {
        for (int i = 0; i < numAccounts; i++) {
            BusinessAccount account = accounts[i];
            if (account.getId().equals(id)) {
                System.out.println("ID: " + account.getId());
                System.out.println("Balance: " + account.getBalance());
            }
        }
    }

}

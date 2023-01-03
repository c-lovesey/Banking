import java.io.*;
import java.time.LocalDate;
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
        String BusinessName = null;
        int id = 0;
        int BusinessAccountid = 0;
        int UserID = 0;
        System.out.println("Business account Creation");
        System.out.println("1. Search with User ID");
        System.out.println("2. Search with User details");

        int choice = scanner.nextInt();
        if (choice == 2) {
            System.out.print("Enter Username: ");
            String Username = scanner.next();
            System.out.print("Enter Firstname: ");
            String Firstname = scanner.next();
            System.out.print("Enter LastName: ");
            String Lastname = scanner.next();
            System.out.print("Enter Year of Birth: ");
            int birthYear = scanner.nextInt();
            System.out.print("Enter month of Birth: ");
            int birthMonth = scanner.nextInt();
            System.out.print("Enter day of Birth: ");
            int birthDay = scanner.nextInt();
            System.out.print("Enter Postcode: ");
            String address = scanner.next();
            if (FindUser.findUser(Firstname, Lastname, LocalDate.of(birthYear, birthMonth, birthDay), address) == null) {
                System.out.println("User not found do you wish to create a new user with this information?");

                System.out.println("Username: " + Username);
                System.out.println("Firstname: " + Firstname);
                System.out.println("Lastname: " + Lastname);
                System.out.println("Date of Birth: " + LocalDate.of(birthYear, birthMonth, birthDay));
                System.out.println("Address: " + address);
                User user = new User(Username, Firstname, Lastname, LocalDate.of(birthYear, birthMonth, birthDay), address);
                user.saveToCSV("users.csv");
                System.out.println("Account Created");
                UserID = Integer.parseInt(FindUser.findUserid(Username));
                System.out.println("");

                try (BufferedReader br = new BufferedReader(new FileReader("BusinessAccounts.csv"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(",");
                        String idCSV = values[0];
                        BusinessAccountid = Integer.parseInt(idCSV) + 1;
                    }
                } catch (IOException e) {
                    BusinessAccountid = 1;
                }
            } else {
                String New = FindUser.findUser(Firstname, Lastname, LocalDate.of(birthYear, birthMonth, birthDay), address);
                if(findID(New) == true){
                    //need to add a check type here
                    System.out.println("User already has an account");

                }
            }
        } else {
            System.out.print("Enter User ID: ");

            UserID = scanner.nextInt();
        }

        System.out.println("Reqister Business");
        System.out.println("1. Search with Business name");
        System.out.println("2. Register new business");
        choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Input Business name:");
                BusinessName = scanner.next();
                if (BusinessCreate.getBusinessInfo(BusinessName) == null){
                    System.out.println("Business not registered");
                    new BusinessCreate();
                }

            case 2:
                new BusinessCreate();
                BusinessName = BusinessCreate.getLastBusiness();
                break;
        }

        String[] CurrentBusiness = BusinessCreate.getBusinessInfo(BusinessName);
        String Businessid = CurrentBusiness[0];


        System.out.print("Enter the balance: ");
        double balance = scanner.nextDouble();
        if (balance < 1) {
            System.out.println("User cannot create an account with less than $1");
        } else {
            saveToCSV("BusinessAccounts.csv",BusinessAccountid, Integer.parseInt(Businessid), UserID, balance);
            System.out.println("ISA Account Created.");
        }

        Menu.main(new String[0]);

    }

    public static boolean findID(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader("BusinessAccounts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String idCSV = values[0];
                if (idCSV.equals(id)) {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
        }

        return false;
    }

    public static void saveToCSV(String fileName, int id, int BusinessID,int UserID, double balance) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {

            String line = id + "," + BusinessID + "," + UserID + "," + balance;
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void viewAccount(String id) {
        for (int i = 0; i < numAccounts; i++) {
            ISAAccount account = accounts[i];
            if (account.getId().equals(id)) {
                System.out.println("ID: " + account.getId());
                System.out.println("Balance: " + account.getBalance());
            }
        }
    }
    private static boolean idExists(String id) {
        for (int i = 0; i < numAccounts; i++) {
            ISAAccount account = accounts[i];
            if (account.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private static void createAccount(String id, double balance, String type) {
        ISAAccount account = new ISAAccount(id, balance);
        accounts[numAccounts] = account;
        numAccounts++;
    }

    private static void printAccounts() {
        for (int i = 0; i < numAccounts; i++) {
            ISAAccount account = accounts[i];
            System.out.println(account.getId() + ": " + account.getBalance() + ": " + "ISA");
        }
    }

    private static void goBack() {
        Menu.main(new String[0]);
    }

    public static int getNumAccounts() {
        return numAccounts;
    }

    public static ISAAccount[] getAccounts() {
        return accounts;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }


}
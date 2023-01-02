import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersonalAccount {
    private static PersonalAccount[] accounts = new PersonalAccount[100];
    private static int numAccounts;

    private double id;
    private double balance;


    public PersonalAccount(double id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public static void main(String[] args) {
        int id = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Personal account Creation");
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
                System.out.println("");

                try (BufferedReader br = new BufferedReader(new FileReader("PersonalAccounts.csv"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(",");
                        String IDCSV = values[0];
                        id = Integer.parseInt(IDCSV) + 1;
                    }
                } catch (IOException e) {
                    id = 1;
                }
            } else {
                id = Integer.parseInt(FindUser.findUser(Firstname, Lastname, LocalDate.of(birthYear, birthMonth, birthDay), address));
            }
        } else {
            System.out.print("Enter ID: ");
            id = scanner.nextInt();
        }


        System.out.print("Enter the balance: ");
        double balance = scanner.nextDouble();
        if (balance < 1) {
            System.out.println("User cannot create an account with less than $1");
        } else {
            String PersonalID = createAccount(id, balance);
            saveToCSV("PersonalAccounts.csv", PersonalID, id, balance);
            System.out.println("Personal Account Created.");
        }

        Menu.main(new String[0]);

    }


    public static String createAccount(int id, double balance) {
        List<String> idList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("PersonalAccounts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                idList.add(values[0]);
            }
        } catch (IOException e) {

        }
        String PersonalId = "";
        if (idList.isEmpty()) {
            PersonalId = "1";
        } else {
            int lastIdInt = Integer.parseInt(idList.get(idList.size() - 1)) + 1;
            PersonalId = Integer.toString(lastIdInt);
        }
        return PersonalId;

    }

    public static void saveToCSV(String fileName, String PersonalID, int id, double balance) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {

            String line = PersonalID + "," + id + "," + balance + "," + "Personal";
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createAccount(Double id, double balance) {
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
    public Double getId() {
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


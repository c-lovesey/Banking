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

    public static void createPersonalAccount() {
        int id = 0;
        Scanner scanner = new Scanner(System.in);//asks how to search for user
        System.out.println("Create Personal Account");
        System.out.println("1. Search with Customer ID");
        System.out.println("2. Search with Customer details");
        System.out.println("3. Back");
        System.out.println("");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.print("Enter Account ID: ");
                id = scanner.nextInt();
                break;
            case 2:
                System.out.print("Enter Username: ");
                String username = scanner.next();
                System.out.print("Enter Firstname: ");
                String firstName = scanner.next();
                System.out.print("Enter LastName: ");
                String lastName = scanner.next();
                System.out.print("Enter Year of Birth: ");
                int birthYear = scanner.nextInt();
                System.out.print("Enter month of Birth: ");
                int birthMonth = scanner.nextInt();
                System.out.print("Enter day of Birth: ");
                int birthDay = scanner.nextInt();
                System.out.print("Enter Postcode: ");
                String address = scanner.next();
                if (CustomerSearch.findCustomer(firstName, lastName, LocalDate.of(birthYear, birthMonth, birthDay), address) == null) {
                    //if customer not in Customers.csv file creates a new customer record
                    System.out.print("Customer not found. Do you wish to create a new user with this information?");
                    String createNew = scanner.next();
                    boolean loop = true;
                    while (loop == true) {
                        switch (createNew.toLowerCase()) {
                            case "yes":
                            case "y":
                                loop = false;
                                System.out.println("Username: " + username);
                                System.out.println("Firstname: " + firstName);
                                System.out.println("Lastname: " + lastName);
                                System.out.println("Date of Birth: " + LocalDate.of(birthYear, birthMonth, birthDay));
                                System.out.println("Address: " + address);
                                Customer customer = new Customer(username, firstName, lastName, LocalDate.of(birthYear, birthMonth, birthDay), address);
                                customer.saveToCSV("Customers.csv");
                                System.out.println("Account Created");
                                System.out.println("");

                                try (BufferedReader br = new BufferedReader(new FileReader("PersonalAccounts.csv"))) {//gets the id value of last in csv file and adds 1
                                    String line;
                                    while ((line = br.readLine()) != null) {
                                        String[] values = line.split(",");
                                        String IDCSV = values[0];
                                        id = Integer.parseInt(IDCSV) + 1;
                                    }
                                } catch (IOException e) {
                                    id = 1;//if no file sets id to 1
                                }

                                break;

                            case "no":
                            case "n":
                                createPersonalAccount();
                                break;

                            default:
                                System.out.println("Invalid input. Please enter Yes (y) or No (n).");
                                break;
                        }
                    }
                } else {
                    id = Integer.parseInt(CustomerSearch.findCustomer(firstName, lastName, LocalDate.of(birthYear, birthMonth, birthDay), address));//if the customer is in the csv file, return customer id
                }
                break;
            case 3:
                BankingApplication.displayMainMenu();
                break;
        }


        System.out.print("Enter the balance: ");//checks if balance is valid
        double balance = scanner.nextDouble();
        if (balance < 1) {
            System.out.println("Customer cannot create an account with less than $1");
        } else {
            String PersonalID = createAccount(id, balance);//if balance is > 1 saves account to csv file
            saveToCSV("PersonalAccounts.csv", PersonalID, id, balance);
            System.out.println("Personal Account Created.");
            System.out.println("");
        }

        BankingApplication.displayMainMenu();

    }


    public static String createAccount(int id, double balance) {//this class gets the id value this one is different as instead of just reading the last value and adding one it creates a list and adds ids to them and gets the length of the list
        List<String> idList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("PersonalAccounts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                idList.add(values[0]);//adds ids to list
            }
        } catch (IOException e) {

        }
        String PersonalId = "";
        if (idList.isEmpty()) {//if no list set id to 1
            PersonalId = "1";
        } else {
            int lastIdInt = Integer.parseInt(idList.get(idList.size() - 1)) + 1;
            PersonalId = Integer.toString(lastIdInt);//gets the new id
        }
        return PersonalId;//returns the new id

    }

    public static void saveToCSV(String fileName, String PersonalID, int id, double balance) {//saves account info to csv file

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {

            String line = PersonalID + "," + id + "," + balance + "," + "Personal";
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //these classes are unused
    private static void printAccounts() {//old code which isnt used
        for (int i = 0; i < numAccounts; i++) {
            PersonalAccount account = accounts[i];
            System.out.println(account.getId() + ": " + account.getBalance());
        }
    }
    private static void goBack() {
        BankingApplication.displayMainMenu();
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


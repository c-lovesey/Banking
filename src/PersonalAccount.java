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
        Scanner scanner = new Scanner(System.in);//asks how to search for user
        System.out.println("Personal account Creation");
        System.out.println("1. Search with User ID");
        System.out.println("2. Search with User details");
        System.out.println("3. Back");
        System.out.println("");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 2:
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
                if (FindUser.findUser(Firstname, Lastname, LocalDate.of(birthYear, birthMonth, birthDay), address) == null) {//if user not in users.csv file creates a new user
                    System.out.println("User not found do you wish to create a new user with this information?");
                    String CreateNew = scanner.next();
                    boolean loop = true;
                    while (loop = true) {
                        switch (CreateNew.toLowerCase()) {
                            case "yes":
                            case "y":
                                System.out.println("Username: " + Username);
                                System.out.println("Firstname: " + Firstname);
                                System.out.println("Lastname: " + Lastname);
                                System.out.println("Date of Birth: " + LocalDate.of(birthYear, birthMonth, birthDay));
                                System.out.println("Address: " + address);
                                User user = new User(Username, Firstname, Lastname, LocalDate.of(birthYear, birthMonth, birthDay), address);
                                user.saveToCSV("users.csv");
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
                                loop = false;
                            case "no":
                            case "n":
                                main(new String[0]);

                            default:
                                System.out.println("Invalid input please type yes or no.");
                        }
                    }
                } else {
                    id = Integer.parseInt(FindUser.findUser(Firstname, Lastname, LocalDate.of(birthYear, birthMonth, birthDay), address));//if the user is in the csv file it gets the id
                }
            case 1:
                System.out.print("Enter ID: ");//asks user for id
                id = scanner.nextInt();
            case 3:
                Main.main(new String[0]);
        }


        System.out.print("Enter the balance: ");//checks if balance is valid
        double balance = scanner.nextDouble();
        if (balance < 1) {
            System.out.println("User cannot create an account with less than $1");
        } else {
            String PersonalID = createAccount(id, balance);//if balance is > 1 saves account to csv file
            saveToCSV("PersonalAccounts.csv", PersonalID, id, balance);
            System.out.println("Personal Account Created.");
            System.out.println("");
        }

        Main.main(new String[0]);

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


    private static void printAccounts() {//old code which isnt used
        for (int i = 0; i < numAccounts; i++) {
            PersonalAccount account = accounts[i];
            System.out.println(account.getId() + ": " + account.getBalance());
        }
    }
    private static void goBack() {
        Main.main(new String[0]);
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


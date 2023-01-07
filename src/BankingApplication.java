import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;

public class BankingApplication {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
//        Alt method for pulling CSV data into array
//        String[][] UsersArray = readCSV("Customers.csv");
//        String[][] PersonalAccount = readCSV("PersonalAccounts.csv");
//        String[][] ISAAccount = readCSV("ISAAccounts.csv");
//        String[][] BusinessAccount = readCSV("BusinessAccounts.csv");
//        String[][] Businesses = readCSV("businesses.csv");
//
//        for (int i = 0; i < Businesses.length; i++) {
//            for (int j = 0; j < Businesses[i].length; j++) {
//                System.out.print(Businesses[i][j] + ",");
//            }
//            System.out.println("");
//        }

        displayMainMenu();
        scanner.close();
    }


    public static String[][] readCSV(String fileName) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String[][] data = null;//created withs size of 1,length of values
        try {
            br = new BufferedReader(new FileReader(fileName));
            int row = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(cvsSplitBy);
                if (data == null) {
                    data = new String[1][values.length];
                } else {
                    data = increaseSize(data);//dynamically increases array size
                }
                for (int i = 0; i < values.length; i++) {
                    data[row][i] = values[i];
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }

    private static String[][] increaseSize(String[][] array) {
        String[][] newArray = new String[array.length + 1][array[0].length];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        return newArray;
    }

    public static void saveToCSV(String fileName, int id, String name, String type) {//same save csv file thing just different lines being saved
        int currentYear = Year.now().getValue();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {

            String line = id + "," + name + "," + type + "," + currentYear;
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // add updateLineById, addToCSV methods here

    public static void displayMainMenu() {
        System.out.println("Banking Application Menu");
        System.out.println("1. Create Account");
        System.out.println("2. Register Business");
        System.out.println("3. View Account");
        System.out.println("4. Direct Debit ");
        System.out.println("5. Update Balance");
        System.out.println("6. Quit");

        System.out.print("\nEnter your choice: ");

        int choice = scanner.nextInt();

        try {
            do {

                switch (choice) {
                    case 1:
                        createAccount();
                        break;
                    case 2:
                        businessCreate();
                        break;
                    case 3:
                        viewAccount();
                        break;
                    case 4:
                        createDirectDebit();
                        break;
                    case 5:
                        changeBalance();

                    case 6:
                        System.out.println("Closing application...");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please choose from options 1-6.");
                }
            } while (choice < 1 || choice > 6);

            scanner.close();

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter an option from the menu.");
            displayMainMenu();
        }
    }

    public static void createAccount() {
        // Account Creation Menu
        System.out.println("\nAccount Creation Menu");
        System.out.println("1. Personal Account");
        System.out.println("2. ISA Account");
        System.out.println("3. Business Account");
        System.out.println("4. New Customer Account");
        System.out.println("5. Back");
        System.out.println("6. Quit");

        System.out.print("\nEnter your choice: ");
        int choice = scanner.nextInt();

        //gets user input and switches to that
        switch (choice) {
            case 1:
                PersonalAccount.createPersonalAccount();
                break;
            case 2:
                ISAAccount.main(new String[0]);
                break;
            case 3:
                BusinessAccount.main(new String[0]);
                break;
            case 4:
                System.out.print("Enter username: ");
                String Username = scanner.next();
                //checking if username is in use to prevent duplicates
                if (CustomerSearch.findUsername(Username)) {
                    System.out.println("Username is in use. Returning to Account Creation menu.");
                    createAccount();
                    break;
                }
                // if username is available, customer details are requested as program continues
                System.out.print("Enter first name: ");
                String firstName = scanner.next();
                System.out.print("Enter last name: ");
                String lastName = scanner.next();
                System.out.print("Enter birth DAY (dd): ");
                int birthDay = scanner.nextInt();
                System.out.print("Enter birth MONTH (mm): ");
                int birthMonth = scanner.nextInt();
                System.out.print("Enter birth YEAR (yyy): ");
                int birthYear = scanner.nextInt();
                System.out.print("Enter postcode: ");
                String postcode = scanner.next();
                /*
                // attempting postcode validation with regex
                // validating postcode format before assigning to variable and proceeding
                if (validatePostcode(postcode)) {
                    postcode = postcodeInput;
                }*/

                //if details are already in system it rejects them if not it creates a new user in the Customers.csv
                if (CustomerSearch.findCustomer(firstName, lastName, LocalDate.of(birthYear, birthMonth, birthDay), postcode) == null)
                {
                    Customer user = new Customer(Username, firstName, lastName, LocalDate.of(birthYear, birthMonth, birthDay), postcode);
                    user.saveToCSV("Customers.csv");
                    System.out.println("Account Created");
                    System.out.println("");
                    displayMainMenu();
                }else{
                    System.out.println("Duplicate information detected within CSV file. Returning to Account Creation menu.");
                    createAccount();
                }
                break;
            case 5:
                System.out.println("\nReturning to main menu...");
                displayMainMenu();
                break;
            case 6:
                System.out.println("Closing application...");
                break;
            default:
                System.out.println("Invalid choice. Please choose from the menu options provided.");
                displayMainMenu();
        }
    }

    public static boolean validatePostcode(String postcode) {
        String postcodeRegexValidation = "^[A-Z]{1,2}[0-9R][0-9A-Z]?[0-9][ABD-HJLNP-UW-Z]{2}$ ";

        if (postcode.matches(postcodeRegexValidation)) {
            System.out.println("--- VALID POSTCODE");
            return true;
        } else {
            System.out.println("Invalid postcode. Returning to Account Creation menu.\n");
            createAccount();
            return false;
        }
    }

    public static void validateBusinessType(String businessName, String type) {
        // validation for business types
        if (type.equalsIgnoreCase("enterprise") || type.equalsIgnoreCase("e")) {
            System.out.println("Enterprises are not supported \nReturning to main menu.");
            BusinessAccount.main(new String[0]);

        } else if (type.equalsIgnoreCase("plc")) {
            System.out.println("Public Limited Companies are not supported \nReturning to business menu.\n");
            BusinessAccount.main(new String[0]);

        } else if (type.equalsIgnoreCase("charity") || type.equalsIgnoreCase("c")) {
            System.out.println("Charities are not supported \nReturning to main menu.\n");
            BusinessAccount.main(new String[0]);

        } else if (type.equalsIgnoreCase("public sector") || type.equalsIgnoreCase("ps")) {
            System.out.println("Public Sector businesses are not supported. No new record added. \nReturning to main menu.\n");
            BusinessAccount.main(new String[0]);

        } else if (type.equalsIgnoreCase("Partnership") || type.equalsIgnoreCase("p")) {
            System.out.println("""
                    Partnerships are supported.
                    A new record has been added for company"""+ " " + businessName);

        } else if (type.equalsIgnoreCase("Sole Trader") || type.equalsIgnoreCase("st")) {
            System.out.println("""
                    Sole Trades are supported.
                    A new record has been added for company"""+ " " + businessName);

        }else if (type.equalsIgnoreCase("Back") || type.equalsIgnoreCase("b")) {
            System.out.println("Back");
            displayMainMenu();

        } else {
            System.out.println("Invalid type.");
            displayMainMenu();
        }
    }
    public static void businessCreate() {
        System.out.println("Register Business");
        System.out.print("Enter business name: ");
        String businessName = scanner.nextLine();

        System.out.print("Enter the business type \n(Sole Trader (ST), Limited Company (LC), Partnership (P), Enterprise (E), Public Limited Company (PLC), Charity (C), Public Sector(PS)): ");
        String type = scanner.nextLine();
        validateBusinessType(businessName, type);

        int id = 1;
        try (BufferedReader br = new BufferedReader(new FileReader("Businesses.csv"))) {//checks for csv file and gets last id value
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String IDCSV = values[0];
                id = Integer.parseInt(IDCSV) + 1;
            }
        } catch (IOException e) {
            id = 1; //if no csv file set to 1
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Businesses.csv", true))){//checks for csv file
            if (getBusinessInfo(businessName)[0].equals("null")) {//if business not in file saves it to file  getBusinessInfo
                saveToCSV("Businesses.csv", id, businessName, type);
                displayMainMenu();
            } else {
                System.out.println("A business with this name already exists.");
            }
        }
        catch (IOException e){
            // if no file exists, the application will create one
            saveToCSV("Businesses.csv", id, businessName, type);

        }
        displayMainMenu();
    }
    public static String[] getBusinessInfo(String name) {//gets the id type and name of business given a name

        try (BufferedReader br = new BufferedReader(new FileReader("Businesses.csv"))) {
            String line;

            while ((line = br.readLine()) != null) {

                String[] values = line.split(",");

                String id = values[0];

                String type = values[2];

                String nameFromFile = values[1];


                if (nameFromFile.equals(name)) {

                    return new String[] {id, type, nameFromFile};//returns all of this
                }
            }
        } catch (IOException e) {
            System.out.println("Csv file not found");;
        }
        return new String[]{"null"};
    }

    public static String getLastBusiness() {//gets the last business in file, forget why I added this
        try (BufferedReader br = new BufferedReader(new FileReader("Businesses.csv"))) {
            String line;
            String lastBusiness = null;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String name = values[2];

                lastBusiness =  name;
            }

            return lastBusiness;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void viewAccount() {
        System.out.print("Enter account type (Personal (p), ISA (i), Business (b)): ");
        String type = scanner.nextLine();

        System.out.println("Search By:");
        System.out.println("1. Customer ID");
        System.out.println("2. Customer Details");
        int choice = scanner.nextInt();

        if (choice == 1) {
            // Requesting user ID to search by
            System.out.print("Enter user ID: ");
            String userId = scanner.next();

            switch (type.toLowerCase()) {
                case "personal":
                case "p":
                    findAccount(userId, "PersonalAccounts.csv", "personal");
                    break;
                case "isa":
                case "i":
                    findAccount(userId,"ISAAccounts.csv", "isa");
                    break;
                case "business":
                case "b":
                    findAccount(userId,"BusinessAccounts.csv", "business");
                    break;
                default:
                    System.out.println("Invalid account type.");
                    break;
            }
        } else {
            String userId = "";
            boolean catchBlockInitiated = false;

            // Requesting user details to search by
            System.out.print("Enter first name: ");
            String firstName = scanner.next();
            System.out.print("Enter last name: ");
            String lastName = scanner.next();
            System.out.print("Enter birth year: ");
            int birthYear = scanner.nextInt();
            System.out.print("Enter birth month: ");
            int birthMonth = scanner.nextInt();
            System.out.print("Enter birth day: ");
            int birthDay = scanner.nextInt();
            System.out.print("Enter postcode: ");
            String postcode = scanner.next();

            try {
                userId = CustomerSearch.findCustomer(firstName, lastName, LocalDate.of(birthYear, birthMonth, birthDay), postcode);
            } catch (DateTimeException e) {
                System.out.println("\nInvalid date has been input. Please try again.\n");
                catchBlockInitiated = true;
            }

            System.out.println("userID " + userId);

            if (userId == null) {
                System.out.println("This user does not exist");
            } else {
                switch (type.toLowerCase()) {
                    case "personal":
                    case "p":
                        findAccount(userId, "PersonalAccounts.csv", "personal");
                        break;
                    case "isa":
                    case "i":
                        findAccount(userId,"ISAAccounts.csv", "isa");
                        break;
                    case "business":
                    case "b":
                        findAccount(userId,"BusinessAccounts.csv", "business");
                        break;
                    default:
                        System.out.println("Invalid account type.");
                        break;
                }
            }
        }
    }

    public static void findAccount(String givenID, String filename, String accountType) {
        //loops through csv file for an id that matches and prints all values stored with the id
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String accountID = values[0];
                if (accountID.equals(givenID)) {
                    System.out.println("\nAccount Information:\n");

                    if (accountType.equals("personal")) {
                        //for (int i = 0; i < line.split(",").length; i++) {
                        //    String personalAccountCSVHeadings = "personalID/accountID,ID/userID,balance,accountType";
                        //    System.out.println(values[i];
                        //}

                        System.out.println("AccountID: " + values[0]);
                        System.out.println("CustomerID: " + values[1]);
                        System.out.println("Balance: " + values[2] + " GBP");
                        System.out.println("Account Type: " + values[3]);
                    }

                    if (accountType.equals("isa")) {
                        System.out.println("AccountID: " + values[0]);
                        System.out.println("CustomerID: " + values[1]);
                        System.out.println("AccountType: " + values[2]);
                        System.out.println("Count: " + values[3]);
                        System.out.println("Total: " + values[4]);
                        System.out.println("Average: " + values[5]);
                    }

                    if (accountType.equals("business")) {
                        System.out.println("AccountID: " + values[0]);
                        System.out.println("ID: " + values[1]);
                        System.out.println("CustomerID: " + values[2]);
                        System.out.println("Balance" + values[3]);
                        System.out.println("Year Created: " + values[4]);
                        System.out.println("Charges: " + values[5]);
                    }

                    System.out.println("Would you like to return to the main menu?");
                    String returnToMenu = scanner.next().toLowerCase();

                    if (returnToMenu.equals("y")) {
                        displayMainMenu();
                    } else {
                        System.out.println("Thank you. \nClosing application...");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to read CSV. \nClosing application.");
        }
    }

    public static void changeBalance() {
        System.out.println("\nUpdate Balance Menu");
        System.out.println("1. Personal account");
        System.out.println("2. ISA account");
        System.out.println("3. Business account");
        System.out.println("4. Back");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        if (choice == 4){
            displayMainMenu();
        }
        // Check if the amount is positive

        System.out.print("Enter the id you want to change the balance of: ");
        String id = scanner.next();
        System.out.print("Enter the amount you want to add or remove: ");
        //double amount = Double.parseDouble(scanner.next());
        String inputAmount = scanner.next();
        double amount = Double.parseDouble(inputAmount);
        String filepath = "";
        double balance = 0;
        int insertPoint = 0;
        switch (choice) {
            case 1:
                filepath = "PersonalAccounts.csv";
                String values[] = accountDetails(id, filepath);
                balance = Double.parseDouble(values[2]);
                balance = calculateBalance(amount, balance);
                insertPoint = 2;
                addToFile(values,insertPoint,balance,id,filepath);
                break;
            case 2:
                filepath = "ISAAccounts.csv";
                values = accountDetails(id, filepath);
                balance = Double.parseDouble(values[2]);
                balance = calculateBalance(amount, balance);
                //ISAAccount.updateAverage(id,Double.parseDouble(values[5]),Integer.parseInt(values[4]),balance);
                insertPoint = 2;
                addToFile(values,insertPoint,balance,id,filepath);
                break;
            case 3:
                filepath = "BusinessAccounts.csv";
                values = accountDetails(id, filepath);
                balance = Double.parseDouble(values[4]);
                balance = calculateBalance(amount, balance);

                insertPoint = 4;
                addToFile(values,insertPoint,balance,id,filepath);

                break;
            default:
                System.out.println("Invalid choice");
                filepath = "";
                break;
        }
        System.out.println("AccountID: " + id + "\n" + "Current Balance:" + balance);
        displayMainMenu();
    }

    public static String[] accountDetails(String id, String filepath) {//gets the id type and name of business given a name
        List<String> idList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            String[] values = new String[0];
            while ((line = br.readLine()) != null) {
                values = line.split(",");
                if (values[0].equals(id)) {
                    for (int i = 0; i < values.length; i++) {
                        idList.add(values[i]);
                    }
                }
            }
            return idList.toArray(values);
        } catch (IOException e) {

        }
        return new String[]{"null"};
    }

    public static double calculateBalance(double amount, double balance) {
        double updatedBalance = 0;

        updatedBalance = balance + amount;


        return updatedBalance;
    }

    static void addToFile(String[] values, int insertPoint, double balance,String id,String filepath){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            if (insertPoint == i) {
                list.add(String.valueOf(balance));
            } else {
                list.add(values[i]);
            }
        }


        try {
            //balance gets to here but doenst update account

            ISAAccount.updateLineById(filepath, id, list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createDirectDebit() {
        System.out.println("Enter the details of the customer setting up the direct.");
        System.out.print("Enter the ID (Customer Account ID): ");
        String id = scanner.nextLine();
        String[] payUser = CustomerSearch.findById(id);

        System.out.println("Enter the details for the recipient account of the direct debit.");
        System.out.print("Enter the payee: ");
        String payee = scanner.nextLine();
        String[] paidUser = CustomerSearch.findById(payee);

        System.out.print("Enter the direct debit amount (GBP): ");
        double amount = scanner.nextDouble();
        payUser[2] = String.valueOf(Double.parseDouble(payUser[2]) - amount);
        paidUser[2] = String.valueOf(Double.parseDouble(paidUser[2]) + amount);
        try {
            ISAAccount.updateLineById("PersonalAccounts.csv",id, List.of(payUser));
            ISAAccount.updateLineById("PersonalAccounts.csv",payee, List.of(paidUser));
        } catch (IOException e) {
            System.out.println("Unable to read CSV. Returning to Direct Debit menu.");
            createDirectDebit();
        }
        displayMainMenu();
    }
}


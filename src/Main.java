import java.io.*;
import java.time.LocalDate;
import java.time.Year;
import java.util.InputMismatchException;
import java.util.Scanner;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[][] UsersArray = readCSV("users.csv");
        String[][] PersonalAccount = readCSV("PersonalAccounts.csv");
        String[][] ISAAccount = readCSV("ISAAccounts.csv");
        String[][] BusinessAccount = readCSV("BusinessAccounts.csv");
        String[][] Businesses = readCSV("businesses.csv");

        for (int i = 0; i < Businesses.length; i++) {
            for (int j = 0; j < Businesses[i].length; j++) {
                System.out.print(Businesses[i][j] + ",");
            }
            System.out.println("");
        }

        Scanner scanner = new Scanner(System.in);

        int choice;

        try {
            do {
                displayMenu();
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        createAccount();
                        break;
                    case 2:
                        businessCreate();
                        break;
                    case 3:
                        ViewAccount.main(new String[0]);
                        break;
                    case 4:
                        DirectDebit.main(new String[0]);
                        break;
                    case 5:
                        StandingOrder.main(new String[0]);
                        break;

                    case 6:
                        System.out.println("""
                                Closing application...
                                Operation complete.""");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please choose from options 1-6.");
                }
            } while (choice < 1 || choice > 6);

            scanner.close();

        } catch (InputMismatchException e) {
            System.out.println("""
                    Invalid input. Please enter a number as referenced in the menu options.
                    Closing application...
                    """);
        }
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

    public static void displayMenu() {
        System.out.println("1. Create account");
        System.out.println("2. Register Business");
        System.out.println("3. View account");
        System.out.println("4. Direct debit ");
        System.out.println("5. Standing order");
        System.out.println("6. Quit");
        System.out.print("\nEnter your choice: ");
    }

    public static void createAccount() {
        Scanner scanner = new Scanner(System.in);
        //create account screen
        System.out.println("1. Personal account");
        System.out.println("2. ISA account");
        System.out.println("3. Business account");
        System.out.println("4. Create new user");
        System.out.println("5. Back");
        System.out.println("6. Quit");

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        //gets user input and switches to that
        switch (choice) {
            case 1:
                PersonalAccount.main(new String[0]);
                break;
            case 2:
                ISAAccount.main(new String[0]);
                break;
            case 3:
                BusinessAccount.main(new String[0]);
                break;
            case 4:

                //code for creating a user is here
                System.out.print("Enter Username: ");
                String Username = scanner.next();
                //checks  if username is taken
                if (FindUser.findUsername(Username) == true) {
                    System.out.println("Username is already taken");
                    main(new String[0]);
                    break;
                }
                //if not asks for all details
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
                //if details are already in system it rejects them if not it creates a new user in the users.csv
                if (FindUser.findUser(Firstname, Lastname, LocalDate.of(birthYear, birthMonth, birthDay), address) == null)
                {
                    User user = new User(Username, Firstname, Lastname, LocalDate.of(birthYear, birthMonth, birthDay), address);
                    user.saveToCSV("users.csv");
                    System.out.println("Account Created");
                    System.out.println("");
                    Main.main(new String[0]);
                }else{
                    System.out.println("A user with these details already exists");
                    main(new String[0]);
                }



                break;
            case 5:
                Main.main(new String[0]);
                break;
            case 6:
                break;
            default:
                System.out.println("Invalid choice.");
                main(new String[0]);
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
            Main.main(new String[0]);

        } else {
            System.out.println("Invalid type.");
            Main.main(new String[0]);
        }
    }
    public static void businessCreate() {
        Scanner scanner = new Scanner(System.in);

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
                Main.main(new String[0]);
            } else {
                System.out.println("A business with this name already exists.");
            }
        }
        catch (IOException e){
            //if no file creates it
            saveToCSV("Businesses.csv", id, businessName, type);

        }
        // return to menu
        Main.main(new String[0]);
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
}


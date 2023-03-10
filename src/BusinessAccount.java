import java.io.*;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BusinessAccount {
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
        String businessName = null;
        int id = 0;
        int businessAccountId = 0;
        int userId = 0;
        //asks user for input
        System.out.println("Business account Creation");
        System.out.println("1. Search with Customer ID");
        System.out.println("2. Search with Customer details");
        System.out.println("3. Back");
        System.out.println("");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                //if option 1 is selected asks for user id input
                System.out.print("Enter Customer ID: ");
                userId = scanner.nextInt();
                break;

            case 2:
                //this part can be sent out to the user class but that class needs to return values so the account can be created
                System.out.print("Enter username: ");
                String username = scanner.next();
                System.out.print("Enter first name: ");
                String firstName = scanner.next();
                System.out.print("Enter last name: ");
                String lastName = scanner.next();
                System.out.print("Enter Year of birth: ");
                int birthYear = scanner.nextInt();
                System.out.print("Enter month of birth: ");
                int birthMonth = scanner.nextInt();
                System.out.print("Enter day of birth: ");
                int birthDay = scanner.nextInt();
                System.out.print("Enter postcode: ");
                String address = scanner.next();
                String Userid = null;
                // conditional statement checking if user is found in the file
                if (CustomerSearch.findCustomer(firstName, lastName, LocalDate.of(birthYear, birthMonth, birthDay), address) == null) {
                    //if user not in Customers.csv file creates a new user
                    System.out.print("Customer not found do you wish to create a new user with this information?");
                    String CreateNew = scanner.next();
                    boolean loop = true;
                    while (loop == true) {
                        switch (CreateNew.toLowerCase()) {
                            case "yes":
                            case "y":
                                loop = false;
                                System.out.println("Invalid Input");
                                System.out.println("");
                                System.out.print("Enter username: ");
                                username = scanner.next();
                                System.out.print("Enter first name: ");
                                firstName = scanner.next();
                                System.out.print("Enter last name: ");
                                lastName = scanner.next();
                                System.out.print("Enter Year of birth: ");
                                birthYear = scanner.nextInt();
                                System.out.print("Enter month of birth: ");
                                birthMonth = scanner.nextInt();
                                System.out.print("Enter day of birth: ");
                                birthDay = scanner.nextInt();
                                System.out.print("Enter postcode: ");
                                address = scanner.next();
                                try (BufferedReader br = new BufferedReader(new FileReader("BusinessAccounts.csv"))) {//gets the id value of last in csv file and adds 1
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
                                main(new String[0]);
                                break;

                            default:
                                System.out.println("Invalid input please type yes or no.");
                                break;
                        }

                    }
                } else {
                    //checks if user is in file
                    System.out.println("Customer found");
                    String New = CustomerSearch.findCustomer(firstName, lastName, LocalDate.of(birthYear, birthMonth, birthDay), address);
                    if (findID(New) == true) {
                        //need to add a check type here//old comment cant remember what i meant
                        System.out.println("Customer already has an account");

                    }
                }
                break;

//            if (Userid == null) {
//                // if user isn't in the file it provides option to add a new user
//                System.out.print("Customer not found. Would you like to create a new user with this information?");
//                String CreateNew = scanner.next();
//                loop = true;
//                while (loop) {
//                    switch (CreateNew.toLowerCase()) {
//                        case "yes":
//                        case "y":
//                            loop = false;
//                            System.out.println("Username: " + username);
//                            System.out.println("First name: " + firstName);
//                            System.out.println("Last name: " + lastName);
//                            System.out.println("Date of Birth: " + LocalDate.of(birthYear, birthMonth, birthDay));
//                            System.out.println("Address: " + address);
//                            //creates the user
//                            Customer user = new Customer(username, firstName, lastName, LocalDate.of(birthYear, birthMonth, birthDay), address);
//                            //saves the user
//                            user.saveToCSV("Customers.csv");
//                            System.out.println("Account Created");
//                            System.out.println("");
//                            //gets the id of the created user (really weird and bad way to do this if we can get it as a return just put it here
//                            userId = Integer.parseInt(CustomerSearch.findUserid(username));
//                            System.out.println("");
//                            //tries to open csv file
//                            try (BufferedReader br = new BufferedReader(new FileReader("BusinessAccounts.csv"))) {
//                                String line;
//                                while ((line = br.readLine()) != null) {//loops through file
//                                    String[] values = line.split(",");//splits it by comma
//                                    String idCSV = values[0];//gets id value
//                                    businessAccountId = Integer.parseInt(idCSV) + 1;//adds 1
//                                    //this just gets the last id value in the file and adds one, can create a variable which just gets the length of the accounts array if done properly
//                                }
//                            } catch (IOException e) {
//                                businessAccountId = 1;//if there is no csv file sets the id to 1
//                            }
//
//                            break;
//                        case "no":
//                        case "n":
//                            main(new String[0]);
//                            break;
//
//                        default:
//                            System.out.println("Invalid input please type yes or no.");
//                            break;
//                    }
//                }


            case 3:
                BankingApplication.displayMainMenu();
                break;
            default:
                System.out.println("Invalid input please type yes or no.");
        }

        //after user has been inputted asks for business to be tied to account
        System.out.println("Register Business");
        System.out.println("1. Search with Business name");
        System.out.println("2. Register new business");
        System.out.println("3. Back");
        System.out.println("");
        System.out.print("Enter your choice: ");
        choice = scanner.nextInt();
        switch (choice) {
            case 1:
                //searches the business csv file the business for the business
                System.out.print("Input Business name:");
                businessName = scanner.next();
                String[] values = null;
                boolean found = false;
                try (BufferedReader br = new BufferedReader(new FileReader("Businesses.csv"))) {//gets the id value of last in csv file and adds 1
                    String line;
                    while ((line = br.readLine()) != null) {
                        values = line.split(",");
                        String name = values[1];
                        if (name.equals(businessName)){
                            found = true;
                        }

                    }
                } catch (IOException e) {
                    found = false;
                }

                if (found == true) {
                    //if the business is found it gets the id of the business
                    System.out.println("Business found");
                    int businessId = Integer.parseInt(values[0]);
                    boolean find = findAccount(Integer.toString(userId));
                    if (find) {//checks if user already has a business account
                        System.out.println("Customer already has an business account.");
                        //this needs to have another check to see if the business has an account associated to it
                        main(new String[0]);
                    } else {
                        //if the user doesn't have a business account asks for balance
                        System.out.print("Enter the balance: ");
                        double balance = scanner.nextDouble();
                        if (balance < 1) {
                            System.out.println("Customer cannot create an account with less than $1. Please deposit a greater amount.");
                        } else {
                            try (BufferedReader br = new BufferedReader(new FileReader("BusinessAccounts.csv"))) {
                                //tries to open csv file to get business account id
                                String line;
                                while ((line = br.readLine()) != null) {
                                    String[] values2 = line.split(",");
                                    String idCSV = values2[0];
                                    businessAccountId = Integer.parseInt(idCSV) + 1;//gets last id value and adds 1
                                }
                            } catch (IOException e) {
                                businessAccountId = 1; // if file doesn't exist, set id to 1 as it'll be the first record added to the file
                            }
                            saveToCSV("BusinessAccounts.csv", businessAccountId, businessId, userId, balance, String.valueOf(Year.now()), "0");
                            System.out.println("Business account Created.");
                        }
                        BankingApplication.displayMainMenu();
                    }


                } else {
                    // if business isn't found creates one, need to work on this as i think program just closes after call
                    System.out.println("Business not found");
                    BankingApplication.businessCreate();
                }

            case 2: // registers new business same problem as last one
                BankingApplication.businessCreate();

                break;
        }
        BankingApplication.displayMainMenu();


    }

    public static boolean findID(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader("BusinessAccounts.csv"))) {//opens csv
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String idCSV = values[0];
                System.out.println(idCSV);
                System.out.println(id);
                if (idCSV.equals(id)) {
                    //if id is in file returns true
                    return true;
                }
            }
        } catch (IOException e) {
            return false;//else return false
        }

        return false;
    }

    public void Charges(String BAid, double sum, int count) {
        try (BufferedReader br = new BufferedReader(new FileReader("BusinessAccounts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String idCSV = values[0];
                int balance = Integer.parseInt(values[3]);//gets balance
                if (idCSV.equals(BAid)) {
                    Year CurrentYear = Year.now();//gets current year
                    int charges = ((Integer.parseInt(String.valueOf(values[4]))))+1;//gets how many charges have been applied
                    int years = ((Integer.parseInt(String.valueOf(CurrentYear))) - (Integer.parseInt(String.valueOf(values[1])))); //gets the number of years since account creation
                    if (years > charges) { //gets the difference between them
                        int num = (years - charges);
                        balance = balance - (num  * 120);//subtracts from the balance
                        List<String> list = new ArrayList<>();
                        list.add(values[0]);
                        list.add(values[1]);
                        list.add(values[2]);
                        list.add(String.valueOf(balance));
                        list.add(values[3]);
                        list.add(String.valueOf(charges + num));


                        updateLineById("BusinessAccounts.csv", values[0], list);
                    }
                }
            }

        }catch (IOException e) {

        }
    }
    public void updateLineById(String filePath, String id, List<String> newValues) throws IOException {
        // Read the CSV file

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder fileContent = new StringBuilder();

            // Iterate through the lines of the CSV file
            while ((line = reader.readLine()) != null) {
                // Split the line into fields
                String[] fields = line.split(",");

                // Check if the ID field matches the ID we are looking for
                if (fields[0].equals(id)) {
                    // Replace the values of the line with the new values
                    fileContent.append(String.join(",", newValues)).append("\n");
                } else {
                    // Keep the original line
                    fileContent.append(line).append("\n");
                }
            }
            reader.close();

            // Write the updated file content to the CSV file
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(fileContent.toString());
            writer.close();
        } catch (IOException e) {
        }
    }
    public static void saveToCSV(String fileName, int id, int BusinessID,int UserID, double balance, String yearCreated, String charges) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {//saves csv file

            String line = id + "," + BusinessID + "," + UserID + "," + balance + "," + yearCreated + "," + charges;
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean findAccount(String id) {//each account creation has functions to find the account, can just pass in
        try (BufferedReader br = new BufferedReader(new FileReader("BusinessAccounts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String userId = values[0];
                if (userId.equals(id)) {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }
    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }


}
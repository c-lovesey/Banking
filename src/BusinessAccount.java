import java.io.*;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BusinessAccount {
    //supposed to create array of objects can be used to load the csv file so it doesnt need to be opened and closed and loaded multiple times
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
        //asks user for input
        System.out.println("Business account Creation");
        System.out.println("1. Search with User ID");
        System.out.println("2. Search with User details");

        int choice = scanner.nextInt();
        if (choice == 2) {//only checks if 2 is pressed can be changed to look better but works
            //this part can be sent out to the user class but that class needs to return values so the account can be created
            //gets inputs
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
            //checks if  user is in the file
            if (FindUser.findUser(Firstname, Lastname, LocalDate.of(birthYear, birthMonth, birthDay), address) == null) {
                //need to fix this so it asks for user input
                System.out.println("User not found do you wish to create a new user with this information?");
                //if user isnt in the file it creates a new user
                System.out.println("Username: " + Username);
                System.out.println("Firstname: " + Firstname);
                System.out.println("Lastname: " + Lastname);
                System.out.println("Date of Birth: " + LocalDate.of(birthYear, birthMonth, birthDay));
                System.out.println("Address: " + address);
                //creates the user
                User user = new User(Username, Firstname, Lastname, LocalDate.of(birthYear, birthMonth, birthDay), address);
                //saves the user
                user.saveToCSV("users.csv");
                System.out.println("Account Created");
                //gets the id of the created user (really weird and bad way to do this if we can get it as a return just put it here
                UserID = Integer.parseInt(FindUser.findUserid(Username));
                System.out.println("");
                //tries to open csv file
                try (BufferedReader br = new BufferedReader(new FileReader("BusinessAccounts.csv"))) {
                    String line;
                    while ((line = br.readLine()) != null) {//loops through file
                        String[] values = line.split(",");//splits it by comma
                        String idCSV = values[0];//gets id value
                        BusinessAccountid = Integer.parseInt(idCSV) + 1;//adds 1
                        //this just gets the last id value in the file and adds one, can create a variable which just gets the length of the accounts array if done properly
                    }
                } catch (IOException e) {
                    BusinessAccountid = 1;//if there is no csv file sets the id to 1
                }
            } else {
                //checks if user is in file
                String New = FindUser.findUser(Firstname, Lastname, LocalDate.of(birthYear, birthMonth, birthDay), address);
                if(findID(New) == true){
                    //need to add a check type here//old comment cant remember what i meant
                    System.out.println("User already has an account");

                }
            }
        } else {
            //if option 1 is selected asks for user id input
            System.out.print("Enter User ID: ");
            UserID = scanner.nextInt();
        }
        //after user has been inputted asks for business to be tied to account
        System.out.println("Reqister Business");
        System.out.println("1. Search with Business name");
        System.out.println("2. Register new business");
        choice = scanner.nextInt();
        switch (choice) {
            case 1:
                //searches the business csv file the business for the business
                System.out.println("Input Business name:");
                BusinessName = scanner.next();
                String[] values = BusinessCreate.getBusinessInfo(BusinessName);

                if (values[2].equals(BusinessName)){
                    //if the business is found it gets the id of the business
                    System.out.println("Business found");
                    String Businessid = values[0];
                    boolean find = findAccount(Integer.toString(UserID));
                    if(find == true){//checks if user already has a business account
                        System.out.println("User already has an Business account");
                        //this needs to have another check to see if the business has an account associated to it
                        main(new String[0]);
                    }
                    else{//if the user doesnt have a business account asks for balance
                        System.out.print("Enter the balance: ");
                        double balance = scanner.nextDouble();
                        if (balance < 1) {//checks if balance is above 0
                            System.out.println("User cannot create an account with less than $1");
                        } else {
                            try (BufferedReader br = new BufferedReader(new FileReader("BusinessAccounts.csv"))) {//tries to open csv file to get id
                                String line;
                                while ((line = br.readLine()) != null) {
                                    String[] values2 = line.split(",");
                                    String idCSV = values2[0];
                                    BusinessAccountid = Integer.parseInt(idCSV) + 1;//gets last id value and adds 1
                                }
                            } catch (IOException e) {
                                BusinessAccountid = 1;//if file doesnt exist set id to 1
                            }
                            saveToCSV("BusinessAccounts.csv",BusinessAccountid, Integer.parseInt(Businessid), UserID, balance, String.valueOf(Year.now()),"0");//save to csv file passes in the account id, the business id, the user id and the balance
                            System.out.println("Business Account Created.");
                        }
                        Menu.main(new String[0]);//fix this :)
                    }


                }
                else {//if business isnt found creates one, need to work on this as i think program just closes after call
                    System.out.println("Business not found");
                    new BusinessCreate();
                }

            case 2://registers new business same problem as last one
                new BusinessCreate();
                BusinessName = BusinessCreate.getLastBusiness();//idk what this does probs nothing cause program closes
                break;
        }
        Menu.main(new String[0]);


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
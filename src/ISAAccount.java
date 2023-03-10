import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ISAAccount {//business account is the most recent account creation class i created so it has more validation that may need to be added here
    private static ISAAccount[] accounts = new ISAAccount[100];//same array of objects that isnt ever used
    private static int numAccounts;

    private String id;
    private double balance;

    public ISAAccount(String id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //asks how to search
        int id = 0;
        System.out.println("ISA account Creation");
        System.out.println("1. Search with Customer ID");
        System.out.println("2. Search with Customer details");
        System.out.println("3. Back");
        System.out.println("");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
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
                    System.out.print("Customer not found do you wish to create a new user with this information?");
                    String CreateNew = scanner.next();
                    boolean loop = true;
                    while (loop == true) {
                        switch (CreateNew.toLowerCase()) {
                            case "yes":
                            case "y":

                                System.out.println("Username: " + username);
                                System.out.println("Firstname: " + firstName);
                                System.out.println("Lastname: " + lastName);
                                System.out.println("Date of Birth: " + LocalDate.of(birthYear, birthMonth, birthDay));
                                System.out.println("Address: " + address);
                                Customer user = new Customer(username, firstName, lastName, LocalDate.of(birthYear, birthMonth, birthDay), address);
                                user.saveToCSV("Customers.csv");
                                System.out.println("Account Created");
                                System.out.println("");

                                try (BufferedReader br = new BufferedReader(new FileReader("ISAAccounts.csv"))) {//gets last id
                                    String line;
                                    while ((line = br.readLine()) != null) {
                                        String[] values = line.split(",");
                                        String IDCSV = values[0];
                                        id = Integer.parseInt(IDCSV) + 1;
                                    }
                                } catch (IOException e) {
                                    id = 1;
                                }
                                loop = false;
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
                } else {//checks if they already have an isa account
                    System.out.println("Customer found");
                    String New = CustomerSearch.findCustomer(firstName, lastName, LocalDate.of(birthYear, birthMonth, birthDay), address);
                    if (findID(New) == true) {
                        System.out.println("Customer already has an ISA account");
                        main(new String[0]);
                    }
                }
                break;
            case 1://asks for id to be entered
                System.out.print("Enter ID: ");
                id = scanner.nextInt();
                break;
            case 3:
                BankingApplication.displayMainMenu();
                break;
            default:
                System.out.println("Invalid input please type yes or no.");
        }

        System.out.print("Enter the balance: ");
        double balance = scanner.nextDouble();
        if (balance < 1) {
            System.out.println("Customer cannot create an account with less than $1");
        } else {
            String ISAID = createAccount(id, balance);//creates a new account
            saveToCSV("ISAAccounts.csv", ISAID, id, balance, 1, balance, 0);//saves account
            System.out.println("ISA Account Created.");
        }

        BankingApplication.displayMainMenu();

    }

    public static boolean findID(String id) {//checks if the id is in the accounts file
        try (BufferedReader br = new BufferedReader(new FileReader("ISAAccounts.csv"))) {
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

    public static String createAccount(int id, double balance) {//very weird idk what i did here but enjoy not sure if works
        List<String> idList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("ISAAccounts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                idList.add(values[0]);
            }
        } catch (IOException e) {

        }
        String ISAID = "";
        if (idList.isEmpty()) {
            ISAID = "1";
        } else {
            int lastIdInt = Integer.parseInt(idList.get(idList.size() - 1)) + 1;
            ISAID = Integer.toString(lastIdInt);
        }
        return ISAID;

    }

    public static void saveToCSV(String fileName, String ISAID, int id, double balance, int count, double total, double average) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {

            String line = ISAID + "," + id + "," + balance + "," + "ISA" +","+ count + "," + total + "," + average;
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void calculateAPR(String isaid){
        try (BufferedReader br = new BufferedReader(new FileReader("ISAAccounts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String idCSV = values[0];
                if (idCSV.equals(isaid)) {
                    double APR = (Integer.parseInt(values[6]) * (0.0275));
                    double balance = Integer.parseInt(values[2] + APR);
                    List<String> list = new ArrayList<>();

                    list.add(values[0]);
                    list.add(String.valueOf(balance));
                    list.add(values[2]);
                    list.add(values[3]);
                    list.add("0");
                    list.add("0");
                    list.add(String.valueOf(balance));

                    updateLineById("accounts.csv", values[0], list);
                }
            }
        }
        catch (IOException e) {

        }

    }
    public static void updateAverage(String isaid, double sum, int count,double balance) {// this would have to be updated every day in order to get the average balance over a year, it only acts as the average balance added
        try (BufferedReader br = new BufferedReader(new FileReader("ISAAccounts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String idCSV = values[0];
                String bal = values[2];
                balance = Double.parseDouble(bal);
                if (idCSV.equals(isaid)) {
                    double total = sum + balance;
                    count++;
                    double average = total / count;
                    List<String> list = new ArrayList<>();

                    list.add(values[0]);
                    list.add(values[1]);
                    list.add(values[2]);
                    list.add(values[3]);
                    list.add(String.valueOf(count));
                    list.add(String.valueOf(total));
                    list.add(String.valueOf(average));
                    updateLineById("ISAAccounts.csv", values[0], list);
                }
            }
        } catch (IOException e) {
            System.out.println("Error updating balance.");
        }
    }

    public static void updateLineById(String filePath, String id, List<String> newValues) throws IOException {
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
                    //System.out.println(newValues);
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

    //these classes are unused
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

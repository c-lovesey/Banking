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
        System.out.println("1. Search with User ID");
        System.out.println("2. Search with User details");
        int choice = scanner.nextInt();
        if (choice == 2) {//same user input probs do need to put this in a method
            System.out.print("Enter Username: ");
            String Username = scanner.nextLine();
            System.out.print("Enter Firstname: ");
            String Firstname = scanner.nextLine();
            System.out.print("Enter LastName: ");
            String Lastname = scanner.nextLine();
            System.out.print("Enter Year of Birth: ");
            int birthYear = scanner.nextInt();
            System.out.print("Enter month of Birth: ");
            int birthMonth = scanner.nextInt();
            System.out.print("Enter day of Birth: ");
            int birthDay = scanner.nextInt();
            System.out.print("Enter Postcode: ");
            String address = scanner.nextLine();
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
            } else {//checks if they already have an isa account
                String New = FindUser.findUser(Firstname, Lastname, LocalDate.of(birthYear, birthMonth, birthDay), address);
                if (findID(New) == true) {
                    System.out.println("User already has an ISA account");
                    main(new String[0]);
                }
            }
        } else {//asks for id to be entered
            System.out.print("Enter ID: ");
            id = scanner.nextInt();
        }


        System.out.print("Enter the balance: ");
        double balance = scanner.nextDouble();
        if (balance < 1) {
            System.out.println("User cannot create an account with less than $1");
        } else {
            String ISAID = createAccount(id, balance);//creates a new account
            saveToCSV("ISAAccounts.csv", ISAID, id, balance,0,balance,0);//saves account
            System.out.println("ISA Account Created.");
        }

        Menu.main(new String[0]);

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

            String line = ISAID + "," + id + "," + balance + "," + "ISA" + count + "," + total + "," + average;
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
    public void updateAverage(String isaid, double sum, int count) {
        try (BufferedReader br = new BufferedReader(new FileReader("ISAAccounts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String idCSV = values[0];
                int balance = Integer.parseInt(values[2]);
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
                    list.add(values[(int) total]);
                    list.add(values[(int) average]);

                    updateLineById("ISAAccounts.csv", values[0], list);
                }
            }
        } catch (IOException e) {

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

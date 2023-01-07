import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class CustomerSearch {
    private String customerId;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;

    public CustomerSearch(String customerId, String username, String firstName, String lastName, LocalDate dateOfBirth, String address) {
        this.customerId = customerId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public static String findCustomer(String firstName, String lastName, LocalDate dateOfBirth, String address) {
        //searches for customer record in Customers.csv
        try (BufferedReader br = new BufferedReader(new FileReader("Customers.csv"))) {//tries to open csv file
            String line;
            while ((line = br.readLine()) != null) {//gets all lines
                String[] values = line.split(",");//splits on the comma
                String customerId = values[0];//gets all the info
                String firstNameFromFile = values[2];
                String lastNameFromFile = values[3];
                LocalDate dateOfBirthFromFile = LocalDate.parse(values[4]);
                String addressFromFile = values[5];

                if (firstNameFromFile.equals(firstName) && lastNameFromFile.equals(lastName) && dateOfBirthFromFile.equals(dateOfBirth) && addressFromFile.equals(address)) {
                    return customerId;//if all the info passed is in the file it returns the customer's id
                }
            }
        } catch (IOException e) {
            return null;//if the file isn't there it returns null
        }

        return null;//if the customer isn't in the file it returns null
    }
    public static boolean findUsername(String username) {//finds username
        try (BufferedReader br = new BufferedReader(new FileReader("Customers.csv"))) {//tries to open csv file
            String line;
            while ((line = br.readLine()) != null) {//loops through csv file
                String[] values = line.split(",");//splits by comma
                String usernameCSV = values[1];//gets username
                if (usernameCSV.equals(username)) {
                    return true;//returns true if name is in Customers.csv
                }
            }
        } catch (IOException e) {
            return false;//returns false if csv file doesnt exist
        }

        return false; //returns false if username isn't in file
    }
    public static String getCustomerId(String username) {
        // gets customerId from username
        try (BufferedReader br = new BufferedReader(new FileReader("Customers.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {//loops through file
                String[] values = line.split(",");//splits by comma
                String usernameCSV = values[1];
                String id = values[0];
                if (usernameCSV.equals(username)) {//if user in file returns id
                    return id;
                }
            }
        } catch (IOException e) {
            return null; // if file does not exist, return null
        }

        return null; // if customer not in file, return null
    }

    public static String[] findById(String customerId) {//finds customers id
        try (BufferedReader br = new BufferedReader(new FileReader("PersonalAccounts.csv"))) {//tries to open csv file
            String line;
            while ((line = br.readLine()) != null) {//loops through file
                String[] values = line.split(",");//splits by comma
                String usernameCSV = values[1];
                String id = values[0];
                if (id.equals(customerId)) {//if customer found, return customerId
                    return values;
                }
            }
        } catch (IOException e) {
            return null; // if file does not exist, return null
        }

        return null; // if customer not in file, return null
    }
}

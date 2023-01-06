import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class FindUser {
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;

    public FindUser(String userId, String username, String firstName, String lastName, LocalDate dateOfBirth, String address) {
        this.userId = userId;//idk what im doing here
        this.username = username;//
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public static String findUser(String firstName, String lastName, LocalDate dateOfBirth, String address) {//finds user in users.csv
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {//tries to open csv file
            String line;
            while ((line = br.readLine()) != null) {//gets all lines
                String[] values = line.split(",");//splits on the comma
                String userId = values[0];//gets all the info
                String firstNameFromFile = values[2];
                String lastNameFromFile = values[3];
                LocalDate dateOfBirthFromFile = LocalDate.parse(values[4]);
                String addressFromFile = values[5];

                if (firstNameFromFile.equals(firstName) && lastNameFromFile.equals(lastName) && dateOfBirthFromFile.equals(dateOfBirth) && addressFromFile.equals(address)) {
                    return userId;//if all the info passed is in the file it returns the users id
                }
            }
        } catch (IOException e) {
            return null;//if the file isnt there it returns null
        }

        return null;//if the user isnt in the file it returns null
    }
    public static boolean findUsername(String username) {//finds username
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {//tries to open csv file
            String line;
            while ((line = br.readLine()) != null) {//loops through csv file
                String[] values = line.split(",");//splits by comma
                String usernameCSV = values[1];//gets username
                if (usernameCSV.equals(username)) {
                    return true;//returns true if name is in users.csv
                }
            }
        } catch (IOException e) {
            return false;//returns false if csv file doesnt exist
        }

        return false;//returns false if username isnt in file
    }
    public static String findUserid(String username) {//finds users id
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {//tries to open csv file
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
            return null; //if csv file not there returns null
        }

        return null; //if user not in file returns null
    }

    public static String[] findByID(String uid) {//finds users id
        try (BufferedReader br = new BufferedReader(new FileReader("PersonalAccounts.csv"))) {//tries to open csv file
            String line;
            while ((line = br.readLine()) != null) {//loops through file
                String[] values = line.split(",");//splits by comma
                String usernameCSV = values[1];
                String id = values[0];
                if (id.equals(uid)) {//if user in file returns id
                    return values;
                }
            }
        } catch (IOException e) {
            return null; //if csv file not there returns null
        }

        return null; //if user not in file returns null
    }
}

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
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public static String findUser(String firstName, String lastName, LocalDate dateOfBirth, String address) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String userId = values[0];
                String firstNameFromFile = values[2];
                String lastNameFromFile = values[3];
                LocalDate dateOfBirthFromFile = LocalDate.parse(values[4]);
                String addressFromFile = values[5];

                if (firstNameFromFile.equals(firstName) && lastNameFromFile.equals(lastName) && dateOfBirthFromFile.equals(dateOfBirth) && addressFromFile.equals(address)) {
                    return userId;
                }
            }
        } catch (IOException e) {
            return null;
        }

        return null;
    }
    public static boolean findUsername(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String usernameCSV = values[1];
                if (usernameCSV.equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
        }

        return false;
    }
    public static String findUserid(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String usernameCSV = values[1];
                String id = values[0];
                if (usernameCSV.equals(username)) {
                    return id;
                }
            }
        } catch (IOException e) {
            return null;
        }

        return null;
    }

}

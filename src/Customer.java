import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String address;
    private LocalDate dateOfBirth;

    public Customer(String username, String firstName, String lastName, LocalDate dateOfBirth, String address) {
        List<String> idList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("Customers.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                idList.add(values[0]);
            }
        } catch (IOException e) {
            System.out.println("Unable to read Customers.csv");
        }

        if (idList.isEmpty()) {
            this.userId = "1";
        } else {
            try {
                int lastIdInt = Integer.parseInt(idList.get(idList.size())) + 1;//sets the users id
                this.userId = Integer.toString(lastIdInt);
            }
            catch (Exception e){
                this.userId = "1";
            }

        }

        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;

    }

    public void createUser(){

    }

    public void saveToCSV(String fileName) {
        //saves to Customers.csv
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {

            String line = userId + "," + username + "," + firstName + "," + lastName + "," + dateOfBirth + "," + address;
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

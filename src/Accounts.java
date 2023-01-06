import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Accounts {

    public void accounts() {
        //class constructor
    }

    // generic createAccount method to be amended (priority 1)
    public static String createAccount(int id, double balance) {
        // if personal, create new personal/add to PersonalAccounts.csv
        // add two other conditionals for creating business and ISA accounts

        List<String> idList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("PersonalAccounts.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                idList.add(values[0]);//adds ids to list
            }
        } catch (IOException e) {

        }
        String PersonalId = "";
        if (idList.isEmpty()) {//if no list set id to 1
            PersonalId = "1";
        } else {
            int lastIdInt = Integer.parseInt(idList.get(idList.size() - 1)) + 1;
            PersonalId = Integer.toString(lastIdInt);//gets the new id
        }
        return PersonalId;//returns the new id

    }
}

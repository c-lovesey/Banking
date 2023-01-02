import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccountManager {
    private static final String COMMA_DELIMITER = ",";

    public static void createAccountFromCSV(String fileName, double balance) {
        List<String> idList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                idList.add(values[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int lastIdInt = Integer.parseInt(idList.get(idList.size() - 1)) + 1;
        int newId = lastIdInt;
        PersonalAccount account = new PersonalAccount(newId, balance);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            String line = newId + COMMA_DELIMITER + Double.toString(balance);
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

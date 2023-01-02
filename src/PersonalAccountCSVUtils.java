import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.FileReader;
        import java.io.FileWriter;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;

public class PersonalAccountCSVUtils {
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    public static void saveAccountsToCSV(List<PersonalAccount> accounts, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (PersonalAccount account : accounts) {
                StringBuilder sb = new StringBuilder();
                sb.append(account.getId());
                sb.append(COMMA_DELIMITER);
                sb.append(account.getBalance());
                sb.append(NEW_LINE_SEPARATOR);
                bw.write(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<PersonalAccount> loadAccountsFromCSV(String fileName) {
        List<PersonalAccount> accounts = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                accounts.add(new PersonalAccount(values[0], Double.parseDouble(values[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    public static void readAccountsFromCSV(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                System.out.println("ID: " + values[0]);
                System.out.println("Balance: " + values[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


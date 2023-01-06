import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Balance {

    public static void changeBalance() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Change Balance");
        System.out.println("1. Personal account");
        System.out.println("2. ISA account");
        System.out.println("3. Business account");
        System.out.println("4. Back");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        if (choice == 4){
            Main.main(new String[0]);
        }
        // Check if the amount is positive

        System.out.print("Enter the id you want to change the balance of: ");
        String id = scanner.next();
        System.out.print("Enter the amount you want to add or remove: ");
        //double amount = Double.parseDouble(scanner.next());
        String inputAmount = scanner.next();
        double amount = Double.parseDouble(inputAmount);
        String filepath = "";
        double balance = 0;
        int insertPoint = 0;
        switch (choice) {
            case 1:
                filepath = "PersonalAccounts.csv";
                String values[] = accountDetails(id, filepath);
                balance = Double.parseDouble(values[2]);
                balance = calcbalance(amount, balance);
                insertPoint = 2;
                addtofile(values,insertPoint,balance,id,filepath);
                break;
            case 2:
                filepath = "ISAAccounts.csv";
                values = accountDetails(id, filepath);
                balance = Double.parseDouble(values[2]);
                balance = calcbalance(amount, balance);
                //ISAAccount.updateAverage(id,Double.parseDouble(values[5]),Integer.parseInt(values[4]),balance);
                insertPoint = 2;
                addtofile(values,insertPoint,balance,id,filepath);
                break;
            case 3:
                filepath = "BusinessAccounts.csv";
                values = accountDetails(id, filepath);
                balance = Double.parseDouble(values[4]);
                balance = calcbalance(amount, balance);

                insertPoint = 4;
                addtofile(values,insertPoint,balance,id,filepath);

                break;
            default:
                System.out.println("Invalid choice");
                filepath = "";
                break;
        }
        System.out.println(balance + " added to id:" + id);
        Main.main(new String[0]);
    }


    public static String[] accountDetails(String id, String filepath) {//gets the id type and name of business given a name
        List<String> idList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            String[] values = new String[0];
            while ((line = br.readLine()) != null) {
                values = line.split(",");
                if (values[0].equals(id)) {
                    for (int i = 0; i < values.length; i++) {
                        idList.add(values[i]);
                    }
                }
            }
            return idList.toArray(values);
        } catch (IOException e) {

        }
        return new String[]{"null"};
    }

    public static double calcbalance(double amount, double balance) {
        double updatedBalance = 0;

        updatedBalance = balance + amount;


        return updatedBalance;
    }

    static void addtofile(String[] values, int insertPoint, double balance,String id,String filepath){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            if (insertPoint == i) {
                list.add(String.valueOf(balance));
            } else {
                list.add(values[i]);
            }
        }


        try {
            //balance gets to here but doenst update account

            ISAAccount.updateLineById(filepath, id, list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
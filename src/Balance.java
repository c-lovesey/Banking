import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Balance {


    //if you want to be fancy you can do an account constructor
//    public void changeBalance(double amount) {
//        // Check if the amount is positive
//        if (amount > 0) {
//            // Add the amount to the balance
//            this.balance += amount;
//        } else {
//            // Check if the balance is sufficient
//            if (this.balance + amount >= 0) {
//                // Subtract the amount from the balance
//                this.balance += amount;
//            }
//        }
//    }

    public static void changeBalance() {
        // Check if the amount is positive
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the id you want to change the balance of: ");
        String id = scanner.next();
        System.out.println("Enter the type of account used: ");
        String type = scanner.next();
        System.out.println("Enter the amount you want to add or remove: ");
        //double amount = Double.parseDouble(scanner.next());
        String inputAmount = scanner.next();
        System.out.println(inputAmount);
        double amount = Double.parseDouble(inputAmount);
        String filepath = "";
        double balance = 0;
        int insertPoint = 0;
        switch (type.toLowerCase()) {
            case "personal":
            case "p":
                filepath = "PersonalAccounts.csv";
                String values[] = accountDetails(id, filepath);
                balance = Double.parseDouble(values[2]);//subtracting from balance dont work?
                balance = calcbalance(amount, balance);
                insertPoint = 2;
                addtofile(values,insertPoint,balance,id,filepath);
                break;
            case "isa":
            case "i":
                filepath = "ISAAccounts.csv";
                values = accountDetails(id, filepath);
                balance = Double.parseDouble(values[2]);
                balance = calcbalance(amount, balance);
                ISAAccount.updateAverage(id,Double.parseDouble(values[5]),Integer.parseInt(values[4]),balance);
                insertPoint = 2;
                addtofile(values,insertPoint,balance,id,filepath);
                break;
            case "business":
            case "b":
                filepath = "BusinessAccounts.csv";
                values = accountDetails(id, filepath);
                balance = Double.parseDouble(values[4]);
                balance = calcbalance(amount, balance);

                insertPoint = 4;
                addtofile(values,insertPoint,balance,id,filepath);

                break;
            default:
                filepath = "";
                break;
        }


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

        System.out.println(balance + " " + " " + amount + " " + updatedBalance);
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
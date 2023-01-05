import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class ViewAccount {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the account type (personal (p), ISA, business (b)): ");
        String type = scanner.nextLine();
        //add a catch for type
        System.out.println("1. Search with User ID");
        System.out.println("2. Search with User details");
        int choice = scanner.nextInt();
        if (choice == 1) {

            System.out.print("Enter the ID: ");
            String id = scanner.next();
            switch (type.toLowerCase()) {
                case "personal":
                case "p":
                    findAccount(id, "PersonalAccounts.csv");
                    break;
                case "isa":
                case "i":
                    findAccount(id,"ISAAccounts.csv");
                    break;
                case "business":
                case "b":
                    findAccount(id,"BusinessAccounts.csv");
                    break;
                default:
                    System.out.println("Invalid account type.");
                    break;
            }
        } else {
            System.out.print("Enter Firstname: ");
            String Firstname = scanner.next();
            System.out.print("Enter LastName: ");
            String Lastname = scanner.next();
            System.out.print("Enter Year of Birth: ");
            int birthYear = scanner.nextInt();
            System.out.print("Enter month of Birth: ");
            int birthMonth = scanner.nextInt();
            System.out.print("Enter day of Birth: ");
            int birthDay = scanner.nextInt();
            System.out.print("Enter Postcode: ");
            String address = scanner.next();
            String Uid = FindUser.findUser(Firstname, Lastname, LocalDate.of(birthYear, birthMonth, birthDay), address);
            if (Uid == null) {
                System.out.println("This user does not exist");
            } else {
                switch (type.toLowerCase()) {
                    case "personal":
                    case "p":
                        findAccount(Uid, "PersonalAccounts.csv");
                        break;
                    case "isa":
                    case "i":
                        findAccount(Uid,"ISAAccounts.csv");
                        break;
                    case "business":
                    case "b":
                        findAccount(Uid,"BusinessAccounts.csv");
                        break;
                    default:
                        System.out.println("Invalid account type.");
                        break;
                }
            }
        }
    }

    public static void findAccount(String id, String filename) {//loops through csv file for an id that matches and prints all values stored

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String Aid = values[0];
                if (Aid.equals(id)) {
                    for (int i = 0; i < line.split(",").length; i++) {
                        System.out.println(values[i]);

                    }
                    return;
                }
            }
        } catch (IOException e) {
            return;
        }
        System.out.println("Account not found");
    }
}
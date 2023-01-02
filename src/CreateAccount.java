import java.time.LocalDate;
import java.util.Scanner;
public class CreateAccount {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Personal account");
        System.out.println("2. ISA account");
        System.out.println("3. Business account");
        System.out.println("4. Create new user");
        System.out.println("5. Back");
        System.out.println("6. Quit");

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                PersonalAccount.main(new String[0]);
                break;
            case 2:
                ISAAccount.main(new String[0]);
                break;
            case 3:
                BusinessAccount.main(new String[0]);
                break;
            case 4:


                System.out.print("Enter Username: ");
                String Username = scanner.next();
                if (FindUser.findUsername(Username) == true) {
                    System.out.println("Username is already taken");
                    main(new String[0]);
                    break;
                }
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
                if (FindUser.findUser(Firstname, Lastname, LocalDate.of(birthYear, birthMonth, birthDay), address) == null)
            {
                User user = new User(Username, Firstname, Lastname, LocalDate.of(birthYear, birthMonth, birthDay), address);
                user.saveToCSV("users.csv");
                System.out.println("Account Created");
                System.out.println("");
                Menu.main(new String[0]);
            }else{
                System.out.println("A user with these details already exists");
                    main(new String[0]);
            }



            break;
            case 5:
                Menu.main(new String[0]);
                break;
            case 6:
                break;
            default:
                System.out.println("Invalid choice.");
                main(new String[0]);
        }
    }
}

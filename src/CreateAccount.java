import java.util.Scanner;
public class CreateAccount {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Personal account");
        System.out.println("2. ISA account");
        System.out.println("3. Business account");
        System.out.println("4. Back");
        System.out.println("5. Quit");
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
                Menu.main(new String[0]);
                break;
            case 5:
                break;
            default:
                System.out.println("Invalid choice.");
                main(new String[0]);
        }
    }
}

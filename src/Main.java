import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int choice;

        try {
            do {
                displayMenu();
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        CreateAccount.main(new String[0]);//calls the selected method //bad way of doing this chadrak pls fix
                        break;
                    case 2:
                        BusinessCreate.main(new String[0]);
                        break;
                    case 3:
                        ViewAccount.main(new String[0]);
                        break;
                    case 4:
                        DirectDebit.main(new String[0]);
                        break;
                    case 5:
                        StandingOrder.main(new String[0]);
                        break;

                    case 6:
                        System.out.println("""
                                Closing application...
                                Operation complete.""");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please choose from options 1-6.");
                }
            } while (choice < 1 || choice > 6);

            scanner.close();

        } catch (InputMismatchException e) {
            System.out.println("""
                    Invalid input. Please enter a number as referenced in the menu options.
                    Closing application...
                    """);
        }
    }

    public static void displayMenu() {
        System.out.println("1. Create account");
        System.out.println("2. Register Business");
        System.out.println("3. View account");
        System.out.println("4. Direct debit ");
        System.out.println("5. Standing order");
        System.out.println("6. Quit");
        System.out.print("\nEnter your choice: ");
    }
}


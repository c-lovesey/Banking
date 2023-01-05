import java.util.Scanner;

public class Menu {//program launches from here
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //initialises scanner to get user input on what to do
        System.out.println("1. Create account");
        System.out.println("2. Register Business");
        System.out.println("3. View account");
        System.out.println("4. Direct debit ");
        System.out.println("5. Standing order");
        System.out.println("6. Quit");
        System.out.print("Enter your choice: ");
        int choice = 0;
        try {
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    CreateAccount.main(new String[0]);//calls the selected method //bad way of doing this chadrack pls fix
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
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    main(new String[0]);
            }
        } catch (Exception e) {
            System.out.println("Invalid input");
            main(new String[0]);//also bad just loop
        }
    }
}


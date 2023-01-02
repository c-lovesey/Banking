import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Create account");
        System.out.println("2. View account");
        System.out.println("3. Direct debit ");
        System.out.println("4. Standing order");
        System.out.println("5. Quit");
        System.out.print("Enter your choice: ");
        int choice = 0;
        try {
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    CreateAccount.main(new String[0]);
                    break;
                case 2:
                    ViewAccount.main(new String[0]);
                    break;
                case 3:
                    DirectDebit.main(new String[0]);
                    break;
                case 4:
                    StandingOrder.main(new String[0]);
                    break;

                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    main(new String[0]);
            }
        } catch (Exception e) {
            System.out.println("Invalid input");
            main(new String[0]);
        }
    }
}


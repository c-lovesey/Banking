import java.util.Scanner;

public class ViewAccount {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the account type (personal, ISA, business): ");
        String type = scanner.nextLine();

        System.out.print("Enter the ID: ");
        String id = scanner.nextLine();

        switch (type) {
            case "personal":
                PersonalAccount.viewAccount(id);
                break;
            case "ISA":
                ISAAccount.viewAccount(id);
                break;
            case "business":
                BusinessAccount.viewAccount(id);
                break;
            default:
                System.out.println("Invalid account type.");
                break;
        }
    }
}

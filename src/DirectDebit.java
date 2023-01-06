import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class DirectDebit {
    private static DirectDebit[] directDebits = new DirectDebit[100];
    private static int numDirectDebits;

    private String id;
    private String payee;
    private double amount;

    public DirectDebit(String id, String payee, double amount) {//done nothing with this do what you want :)
        this.id = id;
        this.payee = payee;
        this.amount = amount;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);



        System.out.print("Enter the ID: ");
        String id = scanner.nextLine();
        String[] PayUser = FindUser.findByID(id);

        System.out.print("Enter the payee: ");
        String payee = scanner.nextLine();
        String[] PaidUser = FindUser.findByID(payee);


        System.out.print("Enter the amount: ");
        double amount = scanner.nextDouble();
        PayUser[2] = String.valueOf(Double.parseDouble(PayUser[2]) - amount);
        PaidUser[2] = String.valueOf(Double.parseDouble(PaidUser[2]) + amount);
        try {

            ISAAccount.updateLineById("PersonalAccounts.csv",id, List.of(PayUser));
            ISAAccount.updateLineById("PersonalAccounts.csv",payee, List.of(PaidUser));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Main.main(new String[0]);
    }
}

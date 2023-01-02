import java.util.Scanner;

public class DirectDebit {
    private static DirectDebit[] directDebits = new DirectDebit[100];
    private static int numDirectDebits;

    private String id;
    private String payee;
    private double amount;

    public DirectDebit(String id, String payee, double amount) {
        this.id = id;
        this.payee = payee;
        this.amount = amount;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter the payee: ");
        String payee = scanner.nextLine();

        System.out.print("Enter the amount: ");
        double amount = scanner.nextDouble();

        createDirectDebit(id, payee, amount);
    }

    private static void createDirectDebit(String id, String payee, double amount) {
        DirectDebit directDebit = new DirectDebit(id, payee, amount);
        directDebits[numDirectDebits] = directDebit;
        numDirectDebits++;
    }

    public static void deleteDirectDebit(String id) {
        for (int i = 0; i < numDirectDebits; i++) {
            DirectDebit directDebit = directDebits[i];
            if (directDebit.getId().equals(id)) {
                directDebits[i] = null;
                numDirectDebits--;
            }
        }
    }

    public static int getNumDirectDebits() {
        return numDirectDebits;
    }

    public static DirectDebit[] getDirectDebits() {
        return directDebits;
    }

    public String getId() {
        return id;
    }

    public String getPayee() {
        return payee;
    }

    public double getAmount() {
        return amount;
    }
}

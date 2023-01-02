import java.util.Scanner;

public class StandingOrder {
    private static StandingOrder[] standingOrders = new StandingOrder[100];
    private static int numStandingOrders;

    private String id;
    private String payee;
    private double amount;

    public StandingOrder(String id, String payee, double amount) {
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

        createStandingOrder(id, payee, amount);
    }

    private static void createStandingOrder(String id, String payee, double amount) {
        StandingOrder standingOrder = new StandingOrder(id, payee, amount);
        standingOrders[numStandingOrders] = standingOrder;
        numStandingOrders++;
    }

    public static void deleteStandingOrder(String id) {
        for (int i = 0; i < numStandingOrders; i++) {
            StandingOrder standingOrder = standingOrders[i];
            if (standingOrder.getId().equals(id)) {
                standingOrders[i] = null;
                numStandingOrders--;
            }
        }
    }

    public static int getNumStandingOrders() {
        return numStandingOrders;
    }

    public static StandingOrder[] getStandingOrders() {
        return standingOrders;
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

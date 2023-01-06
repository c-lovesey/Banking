import java.util.Scanner;

public class BusinessType {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String type = inputType();
        checkType(type);
    }

    public static String inputType() {//this asks the user for the type of business
        System.out.print("Enter the business type (Sole Trader (ST), Limited Company (LC), Partnership (P), Enterprise (E), PLC, Charity (C), Public Sector(PS)): ");
        return scanner.nextLine();
    }

    public static void checkType(String type) {//this checks if the type inputted is valid
        if (type.equalsIgnoreCase("enterprise") || type.equalsIgnoreCase("e")) {
            System.out.println("Enterpirse Businesses are not supported");
            BusinessAccount.main(new String[0]);
        } else if (type.equalsIgnoreCase("plc")) {
            System.out.println("Public Limited Companies are not supported");
            BusinessAccount.main(new String[0]);
        } else if (type.equalsIgnoreCase("charity") || type.equalsIgnoreCase("c")) {
            System.out.println("Charities are not supported");
            BusinessAccount.main(new String[0]);
        } else if (type.equalsIgnoreCase("public sector") || type.equalsIgnoreCase("ps")) {
            System.out.println("Public Sector Businesses are not supported");
            BusinessAccount.main(new String[0]);
        } else if (type.equalsIgnoreCase("Partnership") || type.equalsIgnoreCase("p")) {
                System.out.println("Partnerships are supported");
        } else if (type.equalsIgnoreCase("Sole Trader") || type.equalsIgnoreCase("st")) {
            System.out.println("Sole Traders are supported");

        }else if (type.equalsIgnoreCase("Back") || type.equalsIgnoreCase("b")) {
                System.out.println("Back");
                main(new String[0]);
        } else {
            System.out.println("Invalid type.");
            Main.main(new String[0]);
        }
    }
}

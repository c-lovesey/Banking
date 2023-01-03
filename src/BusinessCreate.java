import java.io.*;
import java.time.Year;
import java.util.Scanner;
import java.time.LocalDate;
public class BusinessCreate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Register Business");
        System.out.print("Enter name of Business: ");
        String BusinessName = scanner.next();
        String type = BusinessType.inputType();
        BusinessType.checkType(type);
        int id = 1;
        try (BufferedReader br = new BufferedReader(new FileReader("businesses.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String IDCSV = values[0];
                id = Integer.parseInt(IDCSV) + 1;
            }
        } catch (IOException e) {
            id = 1;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("businesses.csv", true))){
            if (getBusinessInfo(BusinessName).equals(null)) {
                saveToCSV("businesses.csv", id, BusinessName, type);
                Menu.main(new String[0]);
            } else {
                System.out.println("A business with this name already exists");
            }
        }
        catch (IOException e){
            saveToCSV("businesses.csv", id, BusinessName, type);

        }
        Menu.main(new String[0]);
    }
    public static void saveToCSV(String fileName, int id, String name, String type) {
        int currentYear = Year.now().getValue();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {

            String line = id + "," + name + "," + type + "," + currentYear;
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String[] getBusinessInfo(String name) {
        try (BufferedReader br = new BufferedReader(new FileReader("businesses.csv"))) {
            String line;

            while ((line = br.readLine()) != null) {

                String[] values = line.split(",");

                String id = values[0];

                String type = values[2];

                String nameFromFile = values[1];


                if (nameFromFile.equals(name)) {

                    return new String[] {id, type, nameFromFile};
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getLastBusiness() {
        try (BufferedReader br = new BufferedReader(new FileReader("businesses.csv"))) {
            String line;
            String lastBusiness = null;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String name = values[2];

                lastBusiness =  name;
            }

            return lastBusiness;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}



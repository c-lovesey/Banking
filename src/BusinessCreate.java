import java.io.*;
import java.time.Year;
import java.util.Scanner;
import java.time.LocalDate;
public class BusinessCreate {//adds a business to
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //gets usrr input
        System.out.println("Register Business");
        System.out.print("Enter name of Business: ");
        String BusinessName = scanner.nextLine();
        String type = BusinessType.inputType();
        BusinessType.checkType(type);//checks if the type inputted is valid
        int id = 1;
        try (BufferedReader br = new BufferedReader(new FileReader("Businesses.csv"))) {//checks for csv file and gets last id value
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String IDCSV = values[0];
                id = Integer.parseInt(IDCSV) + 1;
            }
        } catch (IOException e) {
            id = 1; //if no csv file set to 1
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Businesses.csv", true))){//checks for csv file
            if (getBusinessInfo(BusinessName)[0].equals("null")) {//if business not in file saves it to file  getBusinessInfo
                saveToCSV("Businesses.csv", id, BusinessName, type);
                Main.main(new String[0]);
            } else {//else says no
                System.out.println("A business with this name already exists");
            }
        }
        catch (IOException e){//if no file creates it
            saveToCSV("Businesses.csv", id, BusinessName, type);

        }
        Main.main(new String[0]);//returns to menu
    }
    public static void saveToCSV(String fileName, int id, String name, String type) {//same save csv file thing just different lines being saved
        int currentYear = Year.now().getValue();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {

            String line = id + "," + name + "," + type + "," + currentYear;
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String[] getBusinessInfo(String name) {//gets the id type and name of business given a name

        try (BufferedReader br = new BufferedReader(new FileReader("Businesses.csv"))) {
            String line;

            while ((line = br.readLine()) != null) {

                String[] values = line.split(",");

                String id = values[0];

                String type = values[2];

                String nameFromFile = values[1];


                if (nameFromFile.equals(name)) {

                    return new String[] {id, type, nameFromFile};//returns all of this
                }
            }
        } catch (IOException e) {
            System.out.println("Csv file not found");;
        }
        return new String[]{"null"};
    }

    public static String getLastBusiness() {//gets the last business in file, forget why i added this
        try (BufferedReader br = new BufferedReader(new FileReader("Businesses.csv"))) {
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



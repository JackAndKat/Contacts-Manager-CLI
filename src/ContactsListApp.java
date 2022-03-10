import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ContactsListApp {
    public static Scanner sc = new Scanner(System.in);
    public static String directory = "src";
    public static String file = "contacts.txt";
    public static Path directoryPath = Paths.get(directory);
    public static Path filePath = Paths.get(directory, file);
    public static  List<String> contactsList;
    static  {
        try {
            contactsList = Files.readAllLines(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("1. View contacts.\n" +
                "2. Add a new contact.\n" +
                "3. Search a contact by name.\n" +
                "4. Delete an existing contact.\n" +
                "5. Exit.\n" +
                "Enter an option (1, 2, 3, 4 or 5):");
        String userOption = sc.nextLine();
        switch (userOption) {
            case "1":
                for (String contact : contactsList) {
                    System.out.println(contact);
                }
                break;
            case "2":
                System.out.print("Enter Contact: ");
                String nameInput = sc.nextLine();
                System.out.print("Enter your Number: ");
                String numberInput = sc.nextLine();
                String newContact = nameInput + " | " + numberInput;
                Files.write(
                        filePath,
                        Arrays.asList(newContact), StandardOpenOption.APPEND);
                break;
            case "3":
                // search contact by name
                System.out.println("Search for the contact? ");
                String nameSearch = sc.nextLine();
                if (contactsList.contains(nameSearch)) {

                }
                break;
            case "4":
                // delete contact
                List<String> newList = new ArrayList<>();
                System.out.println("Enter contact to delete: ");
                String input = sc.nextLine();
                for (String contact : contactsList) {
                    if (contact.equals(input)) {
                        newList.add("");
                        continue;
                    }
                    newList.add(contact);
                }
                Files.write(filePath,newList);
                break;
            case "5":
                // exit app
                break;
            default:
                System.out.println("Invalid input, try again.");
        }
    }
}

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class ContactsListApp {
    public static Scanner sc = new Scanner(System.in);
    public static String directory = "src";
    public static String file = "contacts.txt";
    public static Path directoryPath = Paths.get(directory);
    public static Path filePath = Paths.get(directory, file);

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
                List<String> contactsList = Files.readAllLines(filePath);
                for (String contact : contactsList) {
                    System.out.println(contact);
                }
                break;
            case "2":
                // add contact
                break;
            case "3":
                // search contact by name
                break;
            case "4":
                // delete contact
                break;
            case "5":
                // exit app
                break;
            default:
                System.out.println("Invalid input, try again.");
        }
    }
}

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class ContactsListApp {
    public static boolean exitApp = false;
    public static Scanner sc = new Scanner(System.in);
    public static String directory = "src";
    public static String file = "contacts.txt";
    public static Path filePath = Paths.get(directory, file);
    public static HashMap<String, String> contactMap = new HashMap<>();

    public static void contactsApp() throws IOException {
        do {
            List<String> contactsList = Files.readAllLines(filePath);
            System.out.print("\n1. View contacts.\n" +
                    "2. Add a new contact.\n" +
                    "3. Search a contact by name.\n" +
                    "4. Delete an existing contact.\n" +
                    "5. Exit.\n" +
                    "Enter an option (1, 2, 3, 4 or 5): ");
            String userOption = sc.nextLine();
            switch (userOption) {
                case "1":
                    // view contacts
                    System.out.println("\nName | Phone number\n------------------");
                    for (String contact : contactsList) {
                        System.out.println(contact);
                    }
                    System.out.println("------------------");
                    break;
                case "2":
                    // add new contact
                    System.out.print("\nEnter contact's name: ");
                    String nameInput = sc.nextLine();
                    System.out.print("Enter contact's number: ");
                    String numberInput = sc.nextLine();
                    String newContact = nameInput + " | " + numberInput;
                    Files.write(
                            filePath,
                            List.of(newContact), StandardOpenOption.APPEND);
                    System.out.println("\nContact added:\n" + newContact);
                    break;
//                updateMap();
//                List<String> newList1 = new ArrayList<>();
//                System.out.print("\nEnter contact's name: ");
//                String nameInput = sc.nextLine();
//                System.out.print("Enter contact's number: ");
//                String numberInput = sc.nextLine();
//                String newContact = nameInput + " | " + numberInput;
//                contactMap.forEach((name, number) -> {
//                    for(String newName : contactsList) {
//                        if (name.equals((nameInput))) {
//                            System.out.printf("There's already a contact named %s. Do you want to overwrite it? (Yes/No)", nameInput);
//                            String confirm = sc.nextLine();
//                            if (confirm.equalsIgnoreCase("y")) {
//                                newList1.add(nameInput);
//                                continue;
//                            }
//                        }
//                        newList1.add(newName);
//                    }
//                    try {
//                        Files.write(filePath, newList1);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                });
////                    String newContact = nameInput + " | " + numberInput;
//                Files.write(
//                        filePath,
//                        List.of(newContact), StandardOpenOption.APPEND);
//                System.out.println("\nContact added:\n" + newContact);
//                break;


                case "3":
                    // search contact by name
                    updateMap();
                    System.out.print("\nSearch for contact by name: ");
                    String nameSearch = sc.nextLine();
                    contactMap.forEach((name, number) -> {
                        if (name.equalsIgnoreCase(nameSearch)) {
                            System.out.printf("\nName | Phone number \n------------------%s %s\n", name, number);
                        }
                    });
                    break;
                case "4":
                    // delete contact
                    updateMap();
                    List<String> newList = new ArrayList<>();
                    System.out.print("\nEnter contact to delete: ");
                    String input = sc.nextLine();
                    contactMap.forEach((name, number) -> {
                        if (name.equalsIgnoreCase(input)) {
                            String deleteContact = name + " " + number;
                            for (String contact : contactsList) {
                                if (!contact.equals(deleteContact)) {
                                    newList.add(contact);
                                }
                            }
                            try {
                                Files.write(filePath,newList);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println("\nContact deleted:\n" + deleteContact);
                        }
                    });
                    break;
                case "5":
                    // exit app
                    System.out.println("\nNow exiting app.");
                    exitApp = true;
                    break;
                default:
                    System.out.println("\nInvalid input, try again.");
            }
        } while (!exitApp);
    }
    public static void updateMap() throws IOException {
        List<String> contactsList = Files.readAllLines(filePath);
        contactMap.clear();
        for (String contact : contactsList) {
            String[] parts = contact.split(" ", 2);
            if (parts.length >= 2) {
                String contactName = parts[0];
                String contactNumber = parts[1];
                contactMap.put(contactName, contactNumber);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        contactsApp();
    }
}

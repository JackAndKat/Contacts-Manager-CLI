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
                    "3. Edit a contact.\n" +
                    "4. Search a contact by name.\n" +
                    "5. Delete an existing contact.\n" +
                    "6. Exit.\n" +
                    "Enter an option (1, 2, 3, 4, 5 or 6): ");
            String userOption = sc.nextLine();
            switch (userOption) {
                case "1":
                    // view contacts
                    contactsList.sort(String.CASE_INSENSITIVE_ORDER);
                    System.out.println("\nName | Phone number |\n---------------------");
                    for (String contact : contactsList) {
                        String[] parts = contact.split(" ", 3);
                        if (parts.length >= 3) {
                            String contactName = parts[0];
                            String divider = parts[1];
                            String contactNumber = parts[2];
                            System.out.printf("%-5s%1s%-14s|%n", contactName,divider,contactNumber.substring(0,3)+
                                    "-"+contactNumber.substring(3,6)+"-"+contactNumber.substring(6));
                        }
                    }
                    System.out.println("---------------------");
                    break;
                case "2":
                    // add new contact
                    updateMap();
                    System.out.print("\nEnter contact's name: ");
                    String nameInput = sc.nextLine();
                    boolean tryAgain;
                    do {
                        System.out.print("Enter contact's number: ");
//                        int numberInput = Integer.parseInt(sc.nextLine());
                        String numberInput = sc.nextLine();
//                        String frmtNum = formatNumber(numberInput);
                        if (numberInput.equals("Invalid")){
                            System.out.println("\nInvalid phone number, try again.");
                            tryAgain = true;
                        } else {
                            tryAgain = false;
                            String newContact = nameInput + " | " + numberInput;
                            contactMap.forEach((name, number) -> {
                                if (name.equalsIgnoreCase(nameInput)) {
                                    System.out.printf("A contact named \"%s\" already exists. Overwrite it? [y/n] ", name);
                                    String overWrite = sc.nextLine();
                                    if (overWrite.equals("y")) {
                                        try {
                                            deleteContact(name);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                            Files.write(
                                    filePath,
                                    List.of(newContact), StandardOpenOption.APPEND);
                            String[] parts = newContact.split(" ",3);
                            String name= parts[0];
                            String divider = parts[1];
                            String number = parts[2];
                            System.out.printf("%nContact added:%n%s %s %s%n" , name ,divider , number.substring(0,3)+
                                    "-"+number.substring(3,6)+"-"+number.substring(6));
//                            System.out.println("\nContact deleted:\n" + newContact);
                        }} while (tryAgain);
                    break;
                case "3":
                    // edit contact
                    editContact();
                    break;
                case "4":
                    // search contact by name
                    updateMap();
                    System.out.print("\nSearch for contact by name: ");
                    String nameSearch = sc.nextLine();
                    contactMap.forEach((name, number) -> {
                        if (name.equalsIgnoreCase(nameSearch)) {
                            System.out.printf("%nName | Phone number %n------------------%n%s %s%n", name, number.substring(0,5)+
                                    "-"+number.substring(5,8)+"-"+number.substring(8));
                        }
                    });
                    break;
                case "5":
                    // delete contact
                    deleteContact();
                    break;
                case "6":
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
    public static String formatNumber(int number) {
        String num = Integer.toString(number);
        int len = num.length();
        if (len == 7) {
            return num.replaceFirst("(\\d{3})(\\d+)", "$1-$2");
        } else if (len == 10) {
            return num.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");
//        } else if (len == 11) {
//            return num.replaceFirst("(\\d)(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3-$4");
        } else {
            return "Invalid";
        }
    }
    public static void deleteContact() throws IOException {
        List<String> contactsList = Files.readAllLines(filePath);
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
                System.out.printf("%nContact deleted:%n%s %s%n" , name , number.substring(0,5)+
                        "-"+number.substring(5,8)+"-"+number.substring(8));
//                System.out.println("\nContact deleted:\n" + deleteContact);
            }
        });
    }
    public static void deleteContact(String deleteName) throws IOException {
        List<String> contactsList = Files.readAllLines(filePath);
        List<String> newList = new ArrayList<>();
        contactMap.forEach((name, number) -> {
            if (name.equalsIgnoreCase(deleteName)) {
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
                System.out.println("\nDeleted old contact info:\n" + deleteContact);
            }
        });
    }
    public static void editContact() throws IOException {
        updateMap();
        System.out.print("\nSearch for contact to edit by name: ");
        String nameSearch = sc.nextLine();
        contactMap.forEach((name, number) -> {
            if (name.equalsIgnoreCase(nameSearch)) {
                System.out.printf("%nName | Phone number %n------------------%n%s %s%n", name, number.substring(0,5)+
                        "-"+number.substring(5,8)+"-"+number.substring(8));
                System.out.println("\nWould you like to edit this contact? [y/n]");
                String editContact = sc.nextLine();
                if (editContact.equals("y")) {
                    System.out.println("Edit name? [y/n]");
                    String editName = sc.nextLine();
                    if (editName.equals("y")) {
                        System.out.println("Enter contact's new name:");
                        String newName = sc.nextLine();
                        System.out.println("Edit number? [y/n]");
                        String editNumber = sc.nextLine();
                        if (editNumber.equals("y")) {
                            boolean tryAgain;
                            do {
                                System.out.print("Enter contact's new number: ");
                                int numberInput = Integer.parseInt(sc.nextLine());
                                String frmtNum = formatNumber(numberInput);
                                if (frmtNum.equals("Invalid")){
                                    System.out.println("\nInvalid phone number, try again.");
                                    tryAgain = true;
                                } else {
                                    tryAgain = false;
                                    String newContact = newName + " | " + frmtNum;
                                    try {
                                        deleteContact(name);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        Files.write(
                                                filePath,
                                                List.of(newContact), StandardOpenOption.APPEND);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println("\nUpdated contact info:\n" + newContact);
                                }} while (tryAgain);
                        } else {
                            String newContact = newName + " " + number;
                            try {
                                deleteContact(name);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                Files.write(
                                        filePath,
                                        List.of(newContact), StandardOpenOption.APPEND);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println("\nUpdated contact info:\n" + newContact);
                        }
                    } else {
                        System.out.println("Edit number? [y/n]");
                        String editNumber = sc.nextLine();
                        if (editNumber.equals("y")) {
                            boolean tryAgain;
                            do {
                                System.out.print("Enter contact's new number: ");
                                int numberInput = Integer.parseInt(sc.nextLine());
                                String frmtNum = formatNumber(numberInput);
                                if (frmtNum.equals("Invalid")){
                                    System.out.println("\nInvalid phone number, try again.");
                                    tryAgain = true;
                                } else {
                                    tryAgain = false;
                                    String newContact = name + " | " + frmtNum;
                                    try {
                                        deleteContact(name);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        Files.write(
                                                filePath,
                                                List.of(newContact), StandardOpenOption.APPEND);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println("\nUpdated contact info:\n" + newContact);
                                }} while (tryAgain);
                        }
                    }
                } else {
                    System.out.println("Edit another contact? [y/n]");
                    String editAnother = sc.nextLine();
                    if (editAnother.equals("y")) {
                        try {
                            editContact();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
    public static void main(String[] args) throws IOException {
        contactsApp();
    }
}

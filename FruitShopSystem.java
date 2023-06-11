package fruitshopsystem;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FruitShopSystem {

    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<Fruit> fruit = new ArrayList<>();
    private static ArrayList<String> IdEntered = new ArrayList<>();
    private static Map<Integer, Fruit> fruitMap = new HashMap<>();
    private static Map<String, ArrayList<Fruit>> personOrder = new HashMap<>();
    private static ArrayList<String> Person = new ArrayList<>();
    private static int option, price, quantity, count, choice, total;
    private static String Id, fruitName, origin, personName;
    private static boolean check;

    public static boolean checkIdExist() {
        for (String entered : IdEntered) {
            if (Id.equalsIgnoreCase(entered)) {
                return true;
            }
        }
        return false;
    }

    public static void inputId() {
        String checkId = "^[A-Za-z]{1}\\d{3}$";
        do {
            System.out.print("Enter Id: ");
            Id = sc.nextLine();
            if (Pattern.matches(checkId, Id) == false) {
                System.out.println("Follow with form: X000");
            } else if (checkIdExist()) {
                System.out.println("Existed!");
            } else {
                break;
            }
        } while (true);
    }

    public static void inputFruitName() {
        do {
            check = false;
            System.out.print("Enter fruit name: ");
            fruitName = sc.nextLine();
            for (char c : fruitName.toCharArray()) {
                if (Character.isDigit(c)) {
                    System.out.println("Names can't have numbers");
                    check = true;
                    break;
                }
            }
        } while (check == true);
    }

    public static void inputPrice() {
        while (true) {
            System.out.print("Enter price: ");
            if (sc.hasNextInt()) {
                price = sc.nextInt();
                break;
            } else {
                System.out.println("Not valid!");
                sc.nextLine();
            }
        }
    }

    public static void inputOrigin() {
        do {
            check = false;
            System.out.print("Enter origin: ");
            origin = sc.nextLine();
            for (char c : origin.toCharArray()) {
                if (Character.isDigit(c)) {
                    System.out.println("Names can't have numbers");
                    check = true;
                    break;
                }
            }
        } while (check == true);
    }

    public static void askUserEnterYesOrNo() {
        String choiceOfUser;
        do {
            check = false;
            System.out.println("Do you want to continue (Y/N)?");
            System.out.print("Enter your choice: ");
            choiceOfUser = sc.nextLine();
            switch (choiceOfUser.toUpperCase()) {
                case "Y":
                    inputFruit(fruit);
                    check = true;
                    break;
                case "N":
                    break;
                default:
                    System.out.println("Not valid! (Y/N)");
                    check = true;
            }
        } while (check == true);

        if (check == false) {
            System.out.printf("%-10s | %-10s | %-5s | %s\n", "ID", "Name", "Price", "Origin");
            for (Fruit fru : fruit) {
                System.out.printf("%-10s   %-10s   $%-5d   %s\n", fru.getId(), fru.getFruitName(), fru.getPrice(), fru.getOrigin());
            }
        }
    }

    public static void inputFruit(ArrayList<Fruit> list) {
        inputId();
        inputFruitName();
        inputPrice();
        sc.nextLine();
        inputOrigin();
        Fruit fruits = new Fruit(Id, fruitName, price, quantity, origin);
        list.add(fruits);
        IdEntered.add(Id);
    }

    public static boolean checkEmpty(String s) {
        if (s.isEmpty()) {
            System.out.println("Cannot be blank!");
            return true;
        }
        return false;
    }

    public static boolean checkSpecialCharacter(String s) {
        if (s.matches(".*[^a-zA-Z ].*")) {
            System.out.println("Names can't have special characters and numbers!");
            return true;
        }
        return false;
    }

    public static void inputPerson() {
        do {
            System.out.print("Input your name: ");
            personName = sc.nextLine();
            if (checkEmpty(personName) == false && checkSpecialCharacter(personName) == false) {
                break;
            }
        } while (true);
        String per = personName;
        Person.add(per);
    }

    public static void sortFruitList() {
        Collections.sort(fruit, new Comparator<Fruit>() {
            @Override
            public int compare(Fruit o1, Fruit o2) {
                return Integer.compare(o1.getPrice(), o2.getPrice());
            }
        });
    }

    public static void displayFruitList() {
        count = 0;
        sortFruitList();
        for (Fruit fru : fruit) {
            count++;
            fruitMap.put(count, fru);
        }
        System.out.println("|++Item++|++Fruit Name++|++Origin++|++Price++|");
        for (int i = 1; i <= count; i++) {
            Fruit displayFruit = fruitMap.get(i);
            System.out.printf("    %-7d   %-9s   %-10s   $%d\n", i, displayFruit.getFruitName(), displayFruit.getOrigin(), displayFruit.getPrice());
        }
    }

    public static void inputCount() {
        check = false;
        while (!check) {
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice >= 1 && choice <= count) {
                    check = true;
                } else {
                    System.out.println("This product is not exist!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Just numbers!");
            }
        }
    }

    public static void inputQuantityOfCustomer() {
        while (true) {
            System.out.print("Please input quantity: ");
            if (sc.hasNextInt()) {
                quantity = sc.nextInt();
                break;
            } else {
                System.out.println("Not valid!");
                sc.nextLine();
            }
        }
    }

    public static void choiceProduct() {
        displayFruitList();
        inputCount();
        for (int i = 1; i <= count; i++) {
            if (choice == i) {
                Fruit displayChoice = fruitMap.get(i);
                System.out.println("You selected: " + displayChoice.getFruitName());
                inputQuantityOfCustomer();
                displayChoice.setQuantity(quantity);
                total = displayChoice.getPrice() * displayChoice.getQuantity();
            }
        }

        String choiceOfUser;
        sc.nextLine();
        do {
            check = false;
            System.out.println("Do you want to order now (Y/N)?");
            System.out.print("Enter your choice: ");
            choiceOfUser = sc.nextLine();
            switch (choiceOfUser.toUpperCase()) {
                case "Y":
                    System.out.println("Product | Quantity | Price | Amount");
                    Fruit displayOrder = fruitMap.get(choice);
                    System.out.printf("%-10s %-10d $%-8d $%d\n", displayOrder.getFruitName(), displayOrder.getQuantity(), displayOrder.getPrice(), total);
                    System.out.println("Total: " + total + "$");
                    inputPerson();
                    ArrayList<Fruit> buyer;
                    if (personOrder.containsKey(personName)) {
                        buyer = personOrder.get(personName);
                    } else {
                        buyer = new ArrayList<>();
                        personOrder.put(personName, buyer);
                    }
                    Fruit fruits = new Fruit(displayOrder.getId(), displayOrder.getFruitName(), displayOrder.getPrice(), displayOrder.getQuantity(), displayOrder.getOrigin());
                    buyer.add(fruits);
                    break;
                case "N":
                    choiceProduct();
                    break;
                default:
                    System.out.println("Not valid! (Y/N)");
                    check = true;
            }
        } while (check == true);
    }

    public static void viewOrder() {
        for (Map.Entry<String, ArrayList<Fruit>> entry : personOrder.entrySet()) {
            int sum = 0;
            String key = entry.getKey();
            ArrayList<Fruit> value = entry.getValue();
            System.out.println("Customer: " + key);
            System.out.println("Product | Quantity | Price | Amount");
            int countOrder = 1;
            for (Fruit order : value) {
                System.out.print(countOrder + ".");
                System.out.printf("%-10s %-10d $%-8d $%d\n", order.getFruitName(), order.getQuantity(), order.getPrice(), order.getPrice() * order.getQuantity());
                sum += order.getPrice() * order.getQuantity();
                countOrder++;
            }
            System.out.println("Total: " + sum + "$");
            System.out.println();
        }
    }

    public static void inputOption() {
        while (true) {
            System.out.print("Enter option: ");
            if (sc.hasNextInt()) {
                option = sc.nextInt();
                break;
            } else {
                System.out.println("Not valid!");
                sc.nextLine();
            }
        }
        sc.nextLine();
    }

    public static void menu() {
        System.out.println("FRUIT SHOP SYSTEM");
        System.out.println("    1. Create Fruit");
        System.out.println("    2. View order");
        System.out.println("    3. Shopping (for buyer)");
        System.out.println("    4. Exit");
        inputOption();
    }

    public static void main(String[] args) {
        do {
            menu();
            switch (option) {
                case 1:
                    inputFruit(fruit);
                    askUserEnterYesOrNo();
                    break;
                case 2:
                    viewOrder();
                    break;
                case 3:
                    choiceProduct();
                    break;
                case 4:
                    System.out.println("Thanks for using!");
                    break;
                default:
                    System.out.println("Not valid!");
            }
        } while (option != 4);
    }

}

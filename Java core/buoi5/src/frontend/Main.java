package frontend;

import java.util.Scanner;

import backend.repository.QLAccount;
import backend.repository.QLDepartment;
import backend.repository.QLPosition;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Hello!");
        menu();
        scanner.close();
    }


    public static void menu() {
        QLAccount qlAccount = new QLAccount();
        QLDepartment qlDepartment = new QLDepartment();
        QLPosition qlPosition = new QLPosition();

        boolean isRunning = true;
        while(isRunning) {
            System.out.println("==== Please choosing one option below ====");
            System.out.println("1. View all accounts");
            System.out.println("2. View all departments");
            System.out.println("3. View all positions");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    ConsolePrinter.printAllAccounts(qlAccount.findAll());
                    break;
                case 2:
                    ConsolePrinter.printAllDepartments(qlDepartment.findAll());
                    break;
                case 3:
                    ConsolePrinter.printAllPositions(qlPosition.findAll());
                    break;
                case 4:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
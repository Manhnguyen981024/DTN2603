package frontend;

import java.util.List;
import java.util.Scanner;

import backend.repository.QLAccount;
import backend.repository.QLDepartment;
import backend.repository.QLPosition;
import entity.Account;
import entity.Department;
import entity.Position;

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

        while(true) {
            System.out.println("==== Please choosing one option below ====");
            System.out.println("1. View all accounts");
            System.out.println("2. View all departments");
            System.out.println("3. View all positions");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    printAllAccounts(qlAccount.findAll());
                    break;
                case 2:
                    printAllDepartments(qlDepartment.findAll());
                    break;
                case 3:
                    printAllPositions(qlPosition.findAll());
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
     

    public static void printAllAccounts(List<Account> accounts) {
        System.out.println("+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+");
        System.out.printf("|%19s |%19s |%19s |%19s |%19s |%19s |%19s |%19s |%19s |\n",
                "Account ID", "Email", "Username", "Full Name", "Department ID", "Department Name",
                "Position ID", "Position Name", "Create Date");
        System.out.printf("+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+\n");
        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    public static void printAllDepartments(List<Department> departments) {
        System.out.println("+--------------------+--------------------+");
        System.out.printf("|%19s |%19s |\n", "Department ID", "Department Name");
        System.out.printf("+--------------------+--------------------+\n");
        for (Department department : departments) {
            System.out.println(department);
        }
    }

    public static void printAllPositions(List<Position> positions) {
        System.out.println("+--------------------+--------------------+");
        System.out.printf("|%19s |%19s |\n", "Position ID", "Position Name");
        System.out.printf("+--------------------+--------------------+\n");
        for (Position position : positions) {
            System.out.println(position);
        }
    }
}
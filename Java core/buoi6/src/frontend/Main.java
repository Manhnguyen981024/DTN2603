package frontend;

import java.util.Scanner;

import backend.repository.QLAccount;
import backend.repository.QLDepartment;
import backend.repository.QLPosition;
import backend.service.AccountService;
import backend.service.DepartmentService;
import backend.service.PositionService;
import entity.Account;
import entity.Department;
import entity.Position;
import entity.enums.Gender;
import entity.enums.PositionName;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        AccountService accountService = new AccountService(
                new QLAccount(),
                new QLDepartment(),
                new QLPosition());

        DepartmentService departmentService = new DepartmentService(
            new QLDepartment()
        );

        PositionService positionService = new PositionService(
            new QLPosition()
        );

        menu(accountService, departmentService, positionService);
        scanner.close();
    }

    public static void menu(AccountService accountService, DepartmentService departmentService, PositionService positionService) {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("==== Please choosing one option below ====");
            System.out.println("1. Accounts management");
            System.out.println("2. Departments management");
            System.out.println("3. Positions management");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    showSubAccountMenu(accountService);
                    break;
                case 2:
                    showSubDepartmentMenu(departmentService);
                    break;
                case 3:
                     showSubPositionMenu(positionService);
                    break;
                case 4:
                    isRunning = false;
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

        }
    }

    public static void showSubPositionMenu(PositionService positionService) {
        while (true) {
            System.out.println("==== Position management ====");
            System.out.println("1. View all positions");
            System.out.println("2. Create position");
            System.out.println("3. Update position name by position id");
            System.out.println("4. Delete position by position id");
            System.out.println("5. Back to main menu");
            int ch = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (ch) {
                case 1:
                    ConsolePrinter.printAllPositions(positionService.findAll());
                    break;
                case 2:
                    createPosition(positionService);
                    break;
                case 3:
                    updatePosition(positionService);
                    break;
                case 4:
                    deletePosition(positionService);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }

    }

    public static void showSubDepartmentMenu(DepartmentService departmentService) {
        while (true) {
            System.out.println("==== Department management ====");
            System.out.println("1. View all departments");
            System.out.println("2. Create department");
            System.out.println("3. Update department name by department id");
            System.out.println("4. Delete department by department id");
            System.out.println("5. Back to main menu");
            int ch = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (ch) {
                case 1:
                    ConsolePrinter.printAllDepartments(departmentService.findAll());
                    break;
                case 2:
                    createDepartment(departmentService);
                    break;
                case 3:
                    updateDepartment(departmentService);
                    break;
                case 4:
                    deleteDepartment(departmentService);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }

    }

    public static void showSubAccountMenu(AccountService accountService) {
        while (true) {
            System.out.println("==== Accounts management ====");
            System.out.println("1. View all accounts");
            System.out.println("2. Create account");
            System.out.println("3. Update username by account id");
            System.out.println("4. Delete account by account id");
            System.out.println("5. Back to main menu");
            int ch = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (ch) {
                case 1:
                    ConsolePrinter.printAllAccounts(accountService.findAll());
                    break;
                case 2:
                    createAccount(accountService);
                    break;
                case 3:
                    updateAccount(accountService);
                    break;
                case 4:
                    deleteAccount(accountService);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }

    }

    public static void createDepartment(DepartmentService departmentService) {
        Department department = new Department();
        System.out.println("==== Create Department ====");
        String departmentName = readString("Enter Department Name: ");
        department.setDepartmentName(departmentName);
        departmentService.createDepartment(department);
    }

    public static void updateDepartment(DepartmentService departmentService) {
        Department department = new Department();
        System.out.println("==== Update Department ====");
        ConsolePrinter.printAllDepartments(departmentService.findAll());

        int departmentId = readInt("Enter department id to update: ");
        String departmentName = readString("Enter new department dame: ");
       
        department.setDepartmentId(departmentId);
        department.setDepartmentName(departmentName);
        departmentService.updateDepartment(department);
    }

    public static void deleteDepartment(DepartmentService departmentService) {
        System.out.println("==== Delete Department ====");
        ConsolePrinter.printAllDepartments(departmentService.findAll());

        int departmentId = readInt("Enter department id to delete: ");
        departmentService.deleteDepartment(departmentId);
    }

    public static void createPosition(PositionService positionService) {
        System.out.println("==== Create Position ====");
        Position position = new Position();
        position.setPositionName(readPositionName().name());
        positionService.createPosition(position);
    }

    public static void updatePosition(PositionService positionService) {
        System.out.println("==== Update Position ====");
        ConsolePrinter.printAllPositions(positionService.findAll());

        Position position = new Position();
        position.setPositionId(readInt("Enter position id to update: "));
        position.setPositionName(readPositionName().name());
        positionService.updatePosition(position);
    }

    public static void deletePosition(PositionService positionService) {
        System.out.println("==== Delete Position ====");
        ConsolePrinter.printAllPositions(positionService.findAll());
        positionService.deletePosition(readInt("Enter position id to delete: "));
    }

    private static PositionName readPositionName() {
        PositionName[] positionNames = PositionName.values();
        for (int index = 0; index < positionNames.length; index++) {
            System.out.println((index + 1) + ". " + positionNames[index]);
        }

        while (true) {
            int choice = readInt("Enter position name: ");
            if (choice >= 1 && choice <= positionNames.length) {
                return positionNames[choice - 1];
            }
            System.out.println("Invalid position choice.");
        }
    }

    public static void createAccount(AccountService accountService) {
        Account account = new Account();
        System.out.println("==== Create Account ====");
        String email = readString("Enter email: ");
        String username = readString("Enter username: ");
        String fullName = readString("Enter full name: ");

        ConsolePrinter.printAllDepartments(new QLDepartment().findAll());
        int departmentId = readInt("Enter department ID: ");

        ConsolePrinter.printAllPositions(new QLPosition().findAll());
        int positionId = readInt("Enter position ID: ");

        System.out.print("Enter gender: ");
        for (Gender gender : Gender.values()) {
            System.out.println(gender.ordinal() + 1 + ". " + gender);
        }
        int genderChoice = readInt("Enter gender choice: ");
        Gender selectedGender = Gender.values()[genderChoice - 1];

        account.setEmail(email);
        account.setUsername(username);
        account.setGender(selectedGender);
        account.setFullName(fullName);
        account.setDepartment(new Department(departmentId));
        account.setPosition(new Position(positionId));
        accountService.createAccount(account);
    }

    public static void updateAccount(AccountService accountService) {
        Account account = new Account();
        System.out.println("==== Update Account ====");


        ConsolePrinter.printAllAccounts(accountService.findAll());
        int accountId = readInt("Enter account ID to update:");
        String username = readString("Enter new username: ");

        account.setAccountId(accountId);
        account.setUsername(username);
        accountService.updateAccount(account);
    }

    public static void deleteAccount(AccountService accountService) {
        System.out.println("==== Delete Account ====");
        ConsolePrinter.printAllAccounts(accountService.findAll());
        int accountId = readInt("Enter account ID to delete:");
        accountService.deleteAccountById(accountId);
    }

    private static String readString(String messageString) {
        System.out.print(messageString);
        return scanner.nextLine().trim();
    }

    private static int readInt(String messageString) {
        System.out.print(messageString);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }
}
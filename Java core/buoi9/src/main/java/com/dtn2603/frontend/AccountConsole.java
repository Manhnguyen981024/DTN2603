package com.dtn2603.frontend;

import com.dtn2603.constant.AppConstants;
import com.dtn2603.controller.AccountController;
import com.dtn2603.controller.DepartmentController;
import com.dtn2603.controller.PositionController;
import com.dtn2603.entity.Account;
import com.dtn2603.entity.Department;
import com.dtn2603.entity.Position;
import com.dtn2603.entity.enums.Gender;
import com.dtn2603.utils.ValidationUtils;
import lombok.AllArgsConstructor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

@AllArgsConstructor
public class AccountConsole {
    private final Scanner scanner = new Scanner(System.in);
    private final AccountController accountController;
    private final DepartmentController departmentController;
    private final PositionController positionController;
    private final InputReader inputReader = new InputReader(scanner);

    public void menu(){
        boolean isRunning = true;
        while(isRunning){
            System.out.println("===== Account Management =====");
            System.out.println("1. View all accounts");
            System.out.println("2. Create account");
            System.out.println("3. Update Account's username");
            System.out.println("4. Delete account by id");
            System.out.println("5. Import account from CSV file");
            System.out.println("6. Exit program");

            System.out.print("Enter your choice: ");
            String option = scanner.nextLine();

            switch(option){
                case "1":
                    this.viewAccounts();
                    break;
                case "2":
                    this.createAccount();
                    break;
                case "3":
                    this.updateAccount();
                    break;
                case "4":
                    this.deleteAccount();
                    break;
                case "5":
                    this.importAccountFromCSV();
                    break;
                case "6":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
        scanner.close();
    }

    private void viewAccounts(){
        List<Account> accounts = accountController.getAllAccount();
        ConsoleView.showAccountsToConsole(accounts);
    }

    private void createAccount(){
        while (true) {
            Account account = new Account();

            String email = inputReader.readEmail("Enter your email address: ");
            String username = inputReader.readStringWithLengthBetween("Enter your username: ", 6, 100);
            String fullname = inputReader.readStringWithLengthBetween("Enter your fullname: ", 6, 100);

            Department department = null;
            System.out.println("===== Please choose one option =====");
            System.out.println("1. Set department later");
            System.out.println("2. View all departments to choose one ID");
            String depOption = scanner.nextLine();
            if ("2".equals(depOption)){
                System.out.println("===== Please view all departments =====");
                List<Department> departments = departmentController.getAllDepartment();
                ConsoleView.showDepartmentsToConsole(departments);
                Integer departmentId = inputReader.readInt("Please input department ID: ");
                department = new Department(departmentId);
            }

            Position position = null;
            System.out.println("===== Please choose one option below =====");
            System.out.println("1. Set position later");
            System.out.println("2. View all positions to choose one ID");
            String posOption = scanner.nextLine();
            if("2".equals(posOption)){
                System.out.println("===== Please view all Positions =====");
                List<Position> positions = positionController.getAllPosition();
                ConsoleView.showPositionsToConsole(positions);
                Integer positionId = inputReader.readInt("Please input position ID: ");
                position = new Position(positionId);
            }

            System.out.println("===== Please choose type gender below =====");
            ConsoleView.showGenderToConsole();
            Gender gender = inputReader.readValidGender();

            account.setGender(gender);
            account.setEmail(email);
            account.setUsername(username);
            account.setFullName(fullname);
            account.setDepartment(department);
            account.setPosition(position);

            if (accountController.addAccount(account)){
                System.out.println("Account created successfully");
                return;
            } else {
                System.out.println("Account creation failed! Do you want to try again? (y/n)");
                String answer = scanner.nextLine();
                if (!answer.toLowerCase().equals("y")){
                    break;
                }
            }
        }

    }

    private void updateAccount(){
        while (true) {
            Account account = new Account();
            Integer accountId = inputReader.readInt("Enter your account id: ");
            String username = inputReader.readStringWithLengthBetween("Enter your new username: ", 6 ,100);

            account.setUsername(username);
            account.setAccountId(accountId);

            if (accountController.updateAccount(account)){
                System.out.println("Account updated successfully");
                return;
            } else {
                System.out.println("Account updated failed! Do you want to again? (y/n)");
                String answer = scanner.nextLine();
                if (!answer.toLowerCase().equals("y")){
                    break;
                }
            }
        }

    }

    private void deleteAccount(){
        while (true) {
            Integer accountId = inputReader.readInt("Enter your account id: ");
            if (accountController.deleteAccount(accountId)){
                System.out.println("Account deleted successfully");
                return;
            } else {
                System.out.println("Account deleted failed! Do you want to continue? (y/n)");
                String answer = scanner.nextLine();
                if (!answer.toLowerCase().equals("y")){
                    break;
                }
            }
        }
    }
    private void importAccountFromCSV(){
        String fileName = inputReader.readString("Enter your file path: ");
        Map<String, Integer> results = accountController.importAccountFromCSV(fileName);
        if (Objects.nonNull(results)){
            System.out.println("==== Import CSV Report ==== ");
            int errors = results.getOrDefault("errorCount", 0);
            System.out.printf("- Total counts: %d\n", results.getOrDefault("totalCount", 0));
            System.out.printf("- Success counts: %d\n", results.getOrDefault("successCount", 0));
            System.out.printf("- Error counts: %d\n", errors);
            if (errors > 0){
                System.out.printf("Please access the path: %s to see the error logs\n " , AppConstants.IMPORT_ACCOUNT_ERROR_LOG);
            }
        } else {
            System.err.println("Import failed! Please try again");
        }
    }
}

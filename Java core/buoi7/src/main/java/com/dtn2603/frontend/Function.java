package com.dtn2603.frontend;

import com.dtn2603.controller.AccountController;
import com.dtn2603.controller.DepartmentController;
import com.dtn2603.controller.PositionController;
import com.dtn2603.entity.Account;
import com.dtn2603.entity.Department;
import com.dtn2603.entity.Position;
import com.dtn2603.entity.enums.Gender;
import com.dtn2603.repository.IAccountRepository;
import com.dtn2603.repository.IDepartmentRepository;
import com.dtn2603.repository.IPositionRepository;
import com.dtn2603.repository.impl.AccountRepositoryImpl;
import com.dtn2603.repository.impl.DepartmentRepositoryImpl;
import com.dtn2603.repository.impl.PositionRepositoryImpl;
import com.dtn2603.service.IAccountService;
import com.dtn2603.service.IDepartmentService;
import com.dtn2603.service.IPositionService;
import com.dtn2603.service.impl.AccountServiceImpl;
import com.dtn2603.service.impl.DepartmentServiceImpl;
import com.dtn2603.service.impl.PositionServiceImpl;

import java.util.List;
import java.util.Scanner;

public class Function {
    private final Scanner scanner = new Scanner(System.in);
    private final AccountController accountController;
    private final DepartmentController departmentController;
    private final PositionController positionController;
    private final InputReader inputReader;

    public Function() {
        IAccountRepository accountRepository = new AccountRepositoryImpl();
        IAccountService accountService = new AccountServiceImpl(accountRepository);
        this.accountController = new AccountController(accountService);

        IDepartmentRepository departmentRepository = new DepartmentRepositoryImpl();
        IDepartmentService departmentService = new DepartmentServiceImpl(departmentRepository);
        this.departmentController = new DepartmentController(departmentService);

        IPositionRepository positionRepository = new PositionRepositoryImpl();
        IPositionService positionService = new PositionServiceImpl(positionRepository);
        this.positionController = new PositionController(positionService);

        inputReader = new InputReader(scanner);
    }

    public void menu(){
        while(true){
            System.out.println("===== Account Management =====");
            System.out.println("1. View all accounts");
            System.out.println("2. Create account");
            System.out.println("3. Update Account's username");
            System.out.println("4. Delete account by id");
            System.out.println("5. Exit program");

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
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private void viewAccounts(){
        List<Account> accounts = accountController.getAllAccount();
        ConsoleView.showAccountsToConsole(accounts);
    }

    private void createAccount(){
        Account account = new Account();
        String email = inputReader.readString("Enter your email address: ");
        String username = inputReader.readString("Enter your username: ");
        String fullname = inputReader.readString("Enter your fullname: ");

        System.out.println("===== Please view all departments =====");
        List<Department> departments = departmentController.getAllDepartment();
        ConsoleView.showDepartmentsToConsole(departments);
        Integer departmentId = inputReader.readValidDepartment(departments);

        System.out.println("===== Please view all Positions =====");
        List<Position> positions = positionController.getAllPosition();
        ConsoleView.showPositionsToConsole(positions);
        Integer positionId = inputReader.readValidPosition(positions);

        System.out.println("===== Please choose type gender below =====");
        ConsoleView.showGenderToConsole();
        Gender gender = inputReader.readValidGender(scanner);

        account.setGender(gender);
        account.setEmail(email);
        account.setUsername(username);
        account.setFullName(fullname);
        account.setDepartment(new Department(departmentId));
        account.setPosition(new Position(positionId));

        if (accountController.addAccount(account)){
            System.out.println("Account created successfully");
        } else {
            System.out.println("Account creation failed");
        }
    }

    private void updateAccount(){
        Account account = new Account();
        Integer accountId = inputReader.readInt("Enter your account id: ");
        String username = inputReader.readString("Enter your new username: ");

        account.setUsername(username);
        account.setAccountId(accountId);

        if (accountController.updateAccount(account)){
            System.out.println("Account updated successfully");
        } else {
            System.out.println("Account updated failed");
        }
    }

    private void deleteAccount(){
        Integer accountId = inputReader.readInt("Enter your account id: ");
        if (accountController.deleteAccount(accountId)){
            System.out.println("Account deleted successfully");
        } else {
            System.out.println("Account deleted failed");
        }
    }

}

package com.dtn2603.frontend;

import java.util.Scanner;

import com.dtn2603.controller.AccountController;
import com.dtn2603.controller.DepartmentController;
import com.dtn2603.controller.PositionController;
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

public class Function {
    private final Scanner scanner = new Scanner(System.in);
    private final AccountController accountController;
    private final DepartmentController departmentController;
    private final PositionController positionController;
    private final AccountConsole accountConsole;
    private final DepartmentConsole departmentConsole;
    private final InputReader inputReader = new InputReader(scanner);

    public Function() {
        IDepartmentRepository departmentRepository = new DepartmentRepositoryImpl();
        IDepartmentService departmentService = new DepartmentServiceImpl(departmentRepository);
        this.departmentController = new DepartmentController(departmentService);

        IPositionRepository positionRepository = new PositionRepositoryImpl();
        IPositionService positionService = new PositionServiceImpl(positionRepository);
        this.positionController = new PositionController(positionService);

        IAccountRepository accountRepository = new AccountRepositoryImpl();
        IAccountService accountService = new AccountServiceImpl(accountRepository, departmentService, positionService);
        this.accountController = new AccountController(accountService);

        accountConsole = new AccountConsole(accountController, departmentController, positionController, inputReader);
        departmentConsole = new DepartmentConsole(departmentController, inputReader);
    }

    public void menu(){
        
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("==== Console App ====");
            System.out.println("1. Account management");
            System.out.println("2. Department management");
            System.out.println("3. Exit");
            
            String choice = inputReader.readString("Enter your choice:");
            switch (choice) {
                case "1":
                    accountConsole.menu();
                    break;
                case "2":
                    departmentConsole.menu();
                    break;
                case "3":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }
}

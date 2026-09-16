package com.dtn2603.frontend;

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
    private final AccountController accountController;
    private final DepartmentController departmentController;
    private final PositionController positionController;
    private final AccountConsole accountConsole;

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

        accountConsole = new AccountConsole(accountController, departmentController, positionController);
    }

    public void menu(){
        accountConsole.menu();
    }
}

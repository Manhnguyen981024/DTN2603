package com.dtn2603.frontend;

import com.dtn2603.entity.Account;
import com.dtn2603.entity.Department;
import com.dtn2603.entity.Position;
import com.dtn2603.entity.enums.Gender;

import java.util.List;

public class ConsoleView {
    public static void showAccountsToConsole(List<Account> accounts){
        System.out.println("+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+");
        System.out.printf("| %19s| %19s| %19s| %19s| %19s| %19s| %19s| %19s|\n", "ID", "Email", "Username", "Fullname", "Department name", "Position name", "Gender", "Create date");
        System.out.println("+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+");
        for(Account account : accounts){
            System.out.printf("| %19s| %19s| %19s| %19s| %19s| %19s| %19s| %19s|\n",
                    account.getAccountId(),
                    account.getEmail(),
                    account.getUsername(),
                    account.getFullName(),
                    account.getDepartment().getDepartmentName(),
                    account.getPosition().getPositionName(),
                    account.getGender().getValue(),
                    account.getCreateDate()
            );
        }
        System.out.println("+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+");
    }

    public static void showDepartmentsToConsole(List<Department> departments){
        System.out.println("+----------+--------------------+");
        System.out.printf("| %9s| %19s|\n", "ID", "Department Name");
        System.out.println("+----------+--------------------+");
        for(Department department : departments){
            System.out.printf("| %9s| %19s|\n", department.getDepartmentId(), department.getDepartmentName());
        }
        System.out.println("+----------+--------------------+");
    }

    public static void showPositionsToConsole(List<Position> positions ){
        System.out.println("+----------+--------------------+");
        System.out.printf("| %9s| %19s|\n", "ID", "Position Name");
        System.out.println("+----------+--------------------+");
        for(Position position : positions){
            System.out.printf("| %9s| %19s|\n", position.getPositionId(), position.getPositionName().name());
        }
        System.out.println("+----------+--------------------+");
    }

    public static void showGenderToConsole(){
        System.out.println("+----------+--------------------+");
        System.out.printf("| %19s| %19s|\n", "Gender Type", "Gender Name");
        System.out.println("+----------+--------------------+");
        for(Gender gender : Gender.values()){
            System.out.printf("| %19s| %19s|\n", gender.name(), gender.getValue());
        }
        System.out.println("+----------+--------------------+");
    }

}

package frontend;

import java.util.List;

import entity.Account;
import entity.Department;
import entity.Position;

public class ConsolePrinter {
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

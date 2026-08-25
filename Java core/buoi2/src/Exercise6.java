import java.time.LocalDate;

import enums.PositionName;

public class Exercise6 {
    public static void run() {
        System.out.println("\n===== EXERCISE 6: METHOD =====");

        // Question 1:
        // Tạo method để in ra các số chẵn nguyên dương nhỏ hơn 10
        System.out.println("\nQuestion 1:");
        printEvenPositiveNumbersBelowTen();

        // Question 2:
        // Tạo method để in thông tin các account
        System.out.println("\nQuestion 2:");
        Department department = new Department(1, "Sale");
        Account[] accounts = {
                new Account(1, "dev1@gmail.com", "dev1", "Nguyễn Văn A",
                        department, new Position(1, PositionName.DEV), LocalDate.now()),
                new Account(2, "tester1@gmail.com", "tester1", "Nguyễn Văn B",
                        department, new Position(2, PositionName.TEST), LocalDate.now())
        };
        printAccounts(accounts);

        // Question 3:
        // Tạo method để in ra các số nguyên dương nhỏ hơn 10
        System.out.println("\nQuestion 3:");
        printPositiveNumbersBelowTen();
    }

    public static void printEvenPositiveNumbersBelowTen() {
        for (int number = 2; number < 10; number += 2) {
            System.out.println(number);
        }
    }

    public static void printAccounts(Account[] accounts) {
        for (Account account : accounts) {
            String departmentName = account.getDepartment() == null
                    ? "Chưa có phòng ban"
                    : account.getDepartment().getDepartmentName();
            System.out.println("Email: " + account.getEmail());
            System.out.println("Full name: " + account.getFullName());
            System.out.println("Phòng ban: " + departmentName);
        }
    }

    public static void printPositiveNumbersBelowTen() {
        for (int number = 1; number < 10; number++) {
            System.out.println(number);
        }
    }
}

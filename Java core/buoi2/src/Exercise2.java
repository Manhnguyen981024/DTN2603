import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import enums.PositionName;

public class Exercise2 {
    public static void run() {
        System.out.println("\n===== EXERCISE 2: SYSTEM.OUT.PRINTF =====");
        // Question 1:
        // Khai báo 1 số nguyên = 5 và sử dụng lệnh System out printf để in ra số
        // nguyên đó
        System.out.println("\nQuestion 1:");
        int number = 5;
        System.out.printf("Số nguyên: %d%n", number);

        // Question 2:
        // Khai báo 1 số nguyên = 100 000 000 và sử dụng lệnh System out printf để in
        // ra số nguyên đó thành định dạng như sau: 100,000,000
        System.out.println("\nQuestion 2:");
        int largeNumber = 100000000;
        System.out.printf("Số nguyên có định dạng: %,d%n", largeNumber);

        // Question 3:
        // Khai báo 1 số thực = 5,567098 và sử dụng lệnh System out printf để in ra số
        // thực đó chỉ bao gồm 4 số đằng sau
        System.out.println("\nQuestion 3:");
        double decimalNumber = 5.567098;
        System.out.printf("Số thực với 4 số sau dấu phẩy: %.4f%n", decimalNumber);


        // Question 4:
        // Khai báo Họ và tên của 1 học sinh và in ra họ và tên học sinh đó theo định
        // dạng như sau:
        // Họ và tên: "Nguyễn Văn A" thì sẽ in ra trên console như sau:
        // Tên tôi là "Nguyễn Văn A" và tôi đang độc thân.
        System.out.println("\nQuestion 4:");
        String fullName = "Nguyễn Văn A";
        System.out.printf("Tên tôi là \"%s\" và tôi đang độc thân.%n", fullName);

        // Question 5:
        // Lấy thời gian bây giờ và in ra theo định dạng sau:
        // 24/04/2020 11h:16p:20s
        System.out.println("\nQuestion 5:");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH'h':mm'p':ss's'");
        System.out.printf("Thời gian hiện tại: %s%n", LocalDateTime.now().format(timeFormatter));


        // Question 6:
        // In ra thông tin account (như Question 8 phần FOREACH) theo định dạng
        // table (giống trong Database)
        System.out.println("\nQuestion 6:");
        Department sale = new Department(1, "Sale");
        Department marketing = new Department(2, "Marketing");
        Account[] accounts = {
                new Account(1, "NguyenVanA@gmail.com", "nguyenvana", "Nguyễn Văn A",
                        sale, new Position(1, PositionName.DEV), LocalDate.of(2026, 8, 1)),
                new Account(2, "NguyenVanB@gmail.com", "nguyen van b", "Nguyễn Văn B",
                        marketing, new Position(2, PositionName.TEST), LocalDate.of(2026, 8, 2))
        };

        System.out.println("+-------------------------+------------------+------------+");
        System.out.printf("| %-23s | %-16s | %-10s |%n", "Email", "FullName", "Department");
        System.out.println("+-------------------------+------------------+------------+");
        for (Account account : accounts) {
            System.out.printf("| %-23s | %-16s | %-10s |%n",
                    account.getEmail(), account.getFullName(),
                    account.getDepartment().getDepartmentName());
        }
        System.out.println("+-------------------------+------------------+------------+");
    }
}

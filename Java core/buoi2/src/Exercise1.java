import java.time.LocalDate;

import enums.PositionName;

public class Exercise1 {
    public static void run() {
        System.out.println("\n===== EXERCISE 1: : Flow Control  =====");
        Department sale = new Department(1, "Sale");
        Department it = new Department(2, "IT");

        Position developer = new Position(1, PositionName.DEV);
        Position tester = new Position(2, PositionName.TEST);

        Account account1 = new Account(1, "dev1@gmail.com", "dev1", "Nguyen Van A",
                sale, developer, LocalDate.of(2026, 8, 1));
        Account account2 = new Account(2, "tester1@gmail.com", "tester1", "Nguyen Van B",
                it, tester, LocalDate.of(2026, 8, 2));

        Group javaFresher = new Group(1, "Java Fresher", account1,
                LocalDate.of(2026, 8, 1));
        Group cSharpFresher = new Group(2, "C# Fresher", account2,
                LocalDate.of(2026, 8, 2));

        GroupAccount[] groupAccounts = {
                new GroupAccount(javaFresher, account2, LocalDate.of(2026, 8, 3)),
                new GroupAccount(cSharpFresher, account2, LocalDate.of(2026, 8, 4))
        };

        // IF
        // Question 1:
        // Kiểm tra account thứ 2
        // Nếu không có phòng ban (tức là department == null) thì sẽ in ra text "Nhân viên này chưa có phòng ban"
        // Nếu không thì sẽ in ra text "Phòng ban của nhân viên này là …"
        System.out.println("Question 1:");
        if (account2.getDepartment() == null) {
            System.out.println("Nhân viên này chưa có phòng ban");
        } else {
            System.out.println("Phòng ban của nhân viên này là "
                    + account2.getDepartment().getDepartmentName());
        }

        //Question 2:
        // Kiểm tra account thứ 2
        // Nếu không có group thì sẽ in ra text "Nhân viên này chưa có group"
        // Nếu có mặt trong 1 hoặc 2 group thì sẽ in ra text "Group của nhân viên này là Java Fresher, C# Fresher"
        // Nếu có mặt trong 3 Group thì sẽ in ra text "Nhân viên này là người quan trọng, tham gia nhiều group"
        // Nếu có mặt trong 4 group trở lên thì sẽ in ra text "Nhân viên này là người hóng chuyện, tham gia tất cả các group"
        System.out.println("\nQuestion 2:");
        int groupCount = countGroups(account2, groupAccounts);
        if (groupCount == 0) {
            System.out.println("Nhân viên này chưa có group");
        } else if (groupCount <= 2) {
            System.out.println("Group của nhân viên này là " + getGroupNames(account2, groupAccounts));
        } else if (groupCount == 3) {
            System.out.println("Nhân viên này là người quan trọng, tham gia nhiều group");
        } else {
            System.out.println("Nhân viên này là người hóng chuyện, tham gia tất cả các group");
        }

        //Question 3:
        // Sử dụng toán tử ternary để làm Question 1
        System.out.println("\nQuestion 3:");
        String departmentMessage = account2.getDepartment() == null
                ? "Nhân viên này chưa có phòng ban"
                : "Phòng ban của nhân viên này là "
                        + account2.getDepartment().getDepartmentName();
        System.out.println(departmentMessage);

        // Question 4:
        // Sử dụng toán tử ternary để làm yêu cầu sau:
        // Kiểm tra Position của account thứ 1
        // Nếu Position = Dev thì in ra text "Đây là Developer"
        // Nếu không phải thì in ra text "Người này không phải là Developer"
        System.out.println("\nQuestion 4:");
        System.out.println(account1.getPosition().getPositionName() == PositionName.DEV
                ? "Đây là Developer"
                : "Người này không phải là Developer");

        // SWITCH CASE
        // Question 5:
        // Lấy ra số lượng account trong nhóm thứ 1 và in ra theo format sau:
        // Nếu số lượng account = 1 thì in ra "Nhóm có một thành viên"
        // Nếu số lượng account = 2 thì in ra "Nhóm có hai thành viên"
        // Nếu số lượng account = 3 thì in ra "Nhóm có ba thành viên"
        // Còn lại in ra "Nhóm có nhiều thành viên"
        System.out.println("\nQuestion 5:");
        int group1AccountCount = 0;
        for (GroupAccount groupAccount : groupAccounts) {
            if (groupAccount.getGroup() == javaFresher) {
                group1AccountCount++;
            }
        }
        if (group1AccountCount == 1) {
            System.out.println("Nhóm có một thành viên");
        } else if (group1AccountCount == 2) {
            System.out.println("Nhóm có hai thành viên");
        } else if (group1AccountCount == 3) {
            System.out.println("Nhóm có ba thành viên");
        } else {
            System.out.println("Nhóm có nhiều thành viên");
        }

        switch (group1AccountCount) {
            case 1:
                System.out.println("Nhóm có một thành viên");
                break;
            case 2:
                System.out.println("Nhóm có hai thành viên");
                break;
            case 3:
                System.out.println("Nhóm có ba thành viên");
                break;
            default:
                System.out.println("Nhóm có nhiều thành viên");
                break;
        }

        //Question 6:
        // Sử dụng switch case để làm lại Question 2
        System.out.println("\nQuestion 6:");
        switch (groupCount) {
            case 0:
                System.out.println("Nhân viên này chưa có group");
                break;
            case 1:
            case 2:
                System.out.println("Group của nhân viên này là "
                        + getGroupNames(account2, groupAccounts));
                break;
            case 3:
                System.out.println("Nhân viên này là người quan trọng, tham gia nhiều group");
                break;
            default:
                System.out.println("Nhân viên này là người hóng chuyện, tham gia tất cả các group");
                break;
        }

        //Question 7:
        //Sử dụng switch case để làm lại Question 4
        System.out.println("\nQuestion 7:");
        switch (account1.getPosition().getPositionName()) {
            case DEV:
                System.out.println("Đây là Developer");
                break;
            default:
                System.out.println("Người này không phải là Developer");
                break;
        }

        // FOREACH
        //Question 8:
        // In ra thông tin các account bao gồm: Email, FullName và tên phòng ban của họ
        Account[] accounts = { account1, account2 };
        Department[] departments = { sale, it };

        System.out.println("\nQuestion 8:");
        System.out.println("+---------------------+------------------+------------+");
        System.out.printf("| %-19s | %-16s | %-10s |%n", "Email", "FullName", "Department");
        System.out.println("+---------------------+------------------+------------+");
        for (Account account : accounts) {
            System.out.printf("| %-19s | %-16s | %-10s |%n",
                    account.getEmail(), account.getFullName(),
                    account.getDepartment().getDepartmentName());
        }
        System.out.println("+---------------------+------------------+------------+");

        //Question 9:
        // In ra thông tin các phòng ban bao gồm: id và name
        System.out.println("\nQuestion 9:");
        System.out.println("+------+--------------+");
        System.out.printf("| %-4s | %-12s |%n", "ID", "Name");
        System.out.println("+------+--------------+");
        for (Department department : departments) {
            System.out.printf("| %-4d | %-12s |%n",
                    department.getDepartmentId(), department.getDepartmentName());
        }
        System.out.println("+------+--------------+");

        // FOR
        //Question 10:
        // In ra thông tin các account bao gồm: Email, FullName và tên phòng ban của
        // họ theo định dạng như sau:
        // Thông tin account thứ 1 là:
        // Email: NguyenVanA@gmail.com
        // Full name: Nguyễn Văn A
        // Phòng ban: Sale
        // Thông tin account thứ 2 là:
        // Email: NguyenVanB@gmail.com
        // Full name: Nguyễn Văn B
        // Phòng ban: Marketting
        System.out.println("\nQuestion 10:");
        for (int index = 0; index < accounts.length; index++) {
            Account account = accounts[index];
            System.out.println("Thông tin account thứ " + (index + 1) + " là:");
            System.out.println("Email: " + account.getEmail());
            System.out.println("Full name: " + account.getFullName());
            System.out.println("Phòng ban: " + account.getDepartment().getDepartmentName());
        }

        // Question 11:
        // In ra thông tin các phòng ban bao gồm: id và name theo định dạng sau:
        // Thông tin department thứ 1 là:
        // Id: 1
        // Name: Sale
        // Thông tin department thứ 2 là:
        // Id: 2
        // Name: Marketing
        System.out.println("\nQuestion 11:");
        for (int index = 0; index < departments.length; index++) {
            Department department = departments[index];
            System.out.println("Thông tin department thứ " + (index + 1) + " là:");
            System.out.println("Id: " + department.getDepartmentId());
            System.out.println("Name: " + department.getDepartmentName());
        }

        // Question 12:
        // Chỉ in ra thông tin 2 department đầu tiên theo định dạng như Question 10
        System.out.println("\nQuestion 12:");
        for (int index = 0; index < Math.min(2, departments.length); index++) {
            Department department = departments[index];
            System.out.println("Thông tin department thứ " + (index + 1) + " là:");
            System.out.println("Id: " + department.getDepartmentId());
            System.out.println("Name: " + department.getDepartmentName());
        }

        // Question 13:
        // In ra thông tin tất cả các account ngoại trừ account thứ 2
        System.out.println("\nQuestion 13:");
        for (int index = 0; index < accounts.length; index++) {
            if (index != 1) {
                Account account = accounts[index];
                System.out.println("Email: " + account.getEmail());
                System.out.println("Full name: " + account.getFullName());
                System.out.println("Phòng ban: " + account.getDepartment().getDepartmentName());
            }
        }

        // Question 14:
        // In ra thông tin tất cả các account có id < 4
        System.out.println("\nQuestion 14:");
        for (int index = 0; index < accounts.length; index++) {
            if (accounts[index].getAccountId() < 4) {
                Account account = accounts[index];
                System.out.println("Email: " + account.getEmail());
                System.out.println("Full name: " + account.getFullName());
                System.out.println("Phòng ban: " + account.getDepartment().getDepartmentName());
            }
        }

        // Question 15:
        // In ra các số chẵn nhỏ hơn hoặc bằng 20
        System.out.println("\nQuestion 15:");
        for (int number = 0; number <= 20; number++) {
            if (number % 2 == 0) {
                System.out.println(number);
            }
        }

        // WHILE
        // Question 16:
        // Làm lại các Question ở phần FOR bằng cách sử dụng WHILE kết hợp với
        // lệnh break, continue
        System.out.println("\nQuestion 16 - WHILE:");
        int index = 0;
        System.out.println("\nQuestion 10:");
        while (index < accounts.length) {
            Account account = accounts[index];
            System.out.println("Thông tin account thứ " + (index + 1) + " là:");
            System.out.println("Email: " + account.getEmail());
            System.out.println("Full name: " + account.getFullName());
            System.out.println("Phòng ban: " + account.getDepartment().getDepartmentName());
            index++;
        }

        index = 0;
        System.out.println("\nQuestion 11:");
        while (index < departments.length) {
            Department department = departments[index];
            System.out.println("Thông tin department thứ " + (index + 1) + " là:");
            System.out.println("Id: " + department.getDepartmentId());
            System.out.println("Name: " + department.getDepartmentName());
            index++;
        }

        index = 0;
        System.out.println("\nQuestion 12:");
        while (index < departments.length) {
            if (index >= 2) {
                break;
            }
            Department department = departments[index];
            System.out.println("Thông tin department thứ " + (index + 1) + " là:");
            System.out.println("Id: " + department.getDepartmentId());
            System.out.println("Name: " + department.getDepartmentName());
            index++;
        }

        index = 0;
        System.out.println("\nQuestion 13:");
        while (index < accounts.length) {
            if (index == 1) {
                index++;
                continue;
            }
            Account account = accounts[index];
            System.out.println("Email: " + account.getEmail());
            System.out.println("Full name: " + account.getFullName());
            System.out.println("Phòng ban: " + account.getDepartment().getDepartmentName());
            index++;
        }

        index = 0;
        System.out.println("\nQuestion 14:");
        while (index < accounts.length) {
            if (accounts[index].getAccountId() >= 4) {
                index++;
                continue;
            }
            Account account = accounts[index];
            System.out.println("Email: " + account.getEmail());
            System.out.println("Full name: " + account.getFullName());
            System.out.println("Phòng ban: " + account.getDepartment().getDepartmentName());
            index++;
        }

        int number = 0;
        System.out.println("\nQuestion 15:");
        while (number <= 20) {
            if (number % 2 != 0) {
                number++;
                continue;
            }
            System.out.println(number);
            number++;
        }

        // DO-WHILE
        // Question 17:
        // Làm lại các Question ở phần FOR bằng cách sử dụng DO-WHILE kết hợp với
        // lệnh break, continue
        System.out.println("\nQuestion 17 - DO-WHILE:");
        index = 0;
        System.out.println("Question 10:");
        if (accounts.length > 0) {
            do {
                Account account = accounts[index];
                System.out.println("Thông tin account thứ " + (index + 1) + " là:");
                System.out.println("Email: " + account.getEmail());
                System.out.println("Full name: " + account.getFullName());
                System.out.println("Phòng ban: " + account.getDepartment().getDepartmentName());
                index++;
            } while (index < accounts.length);
        }

        index = 0;
        System.out.println("\nQuestion 11:");
        if (departments.length > 0) {
            do {
                Department department = departments[index];
                System.out.println("Thông tin department thứ " + (index + 1) + " là:");
                System.out.println("Id: " + department.getDepartmentId());
                System.out.println("Name: " + department.getDepartmentName());
                index++;
            } while (index < departments.length);
        }

        index = 0;
        System.out.println("\nQuestion 12:");
        if (departments.length > 0) {
            do {
                if (index >= 2) {
                    break;
                }
                Department department = departments[index];
                System.out.println("Thông tin department thứ " + (index + 1) + " là:");
                System.out.println("Id: " + department.getDepartmentId());
                System.out.println("Name: " + department.getDepartmentName());
                index++;
            } while (index < departments.length);
        }

        index = 0;
        System.out.println("\nQuestion 13:");
        if (accounts.length > 0) {
            do {
                if (index == 1) {
                    index++;
                    continue;
                }
                Account account = accounts[index];
                System.out.println("Email: " + account.getEmail());
                System.out.println("Full name: " + account.getFullName());
                System.out.println("Phòng ban: " + account.getDepartment().getDepartmentName());
                index++;
            } while (index < accounts.length);
        }

        index = 0;
        System.out.println("\nQuestion 14:");
        if (accounts.length > 0) {
            do {
                if (accounts[index].getAccountId() >= 4) {
                    index++;
                    continue;
                }
                Account account = accounts[index];
                System.out.println("Email: " + account.getEmail());
                System.out.println("Full name: " + account.getFullName());
                System.out.println("Phòng ban: " + account.getDepartment().getDepartmentName());
                index++;
            } while (index < accounts.length);
        }

        number = 0;
        System.out.println("\nQuestion 15:");
        do {
            if (number % 2 != 0) {
                number++;
                continue;
            }
            System.out.println(number);
            number++;
        } while (number <= 20);
    }

    private static int countGroups(Account account, GroupAccount[] groupAccounts) {
        int count = 0;
        for (GroupAccount groupAccount : groupAccounts) {
            if (groupAccount.getAccount() == account) {
                count++;
            }
        }
        return count;
    }

    private static String getGroupNames(Account account, GroupAccount[] groupAccounts) {
        StringBuilder groupNames = new StringBuilder();
        for (GroupAccount groupAccount : groupAccounts) {
            if (groupAccount.getAccount() == account) {
                if (groupNames.length() > 0) {
                    groupNames.append(", ");
                }
                groupNames.append(groupAccount.getGroup().getGroupName());
            }
        }
        return groupNames.toString();
    }
}

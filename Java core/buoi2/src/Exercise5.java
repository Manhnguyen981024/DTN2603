import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.Scanner;

import enums.PositionName;

public class Exercise5 {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void run() {
        System.out.println("\n===== EXERCISE 5: INPUT FROM CONSOLE =====");

        // Question 1:Viết lệnh cho phép người dùng nhập 3 số nguyên vào chương trình.
        inputThreeIntegers();

        // Question 2:Viết lệnh cho phép người dùng nhập 2 số thực vào chương trình.
        inputTwoDoubles();

        // Question 3:Viết lệnh cho phép người dùng nhập họ và tên.
        inputFullName();

        // Question 4:Viết lệnh cho phép người dùng nhập vào ngày sinh nhật của họ.
        inputBirthday();

        // Question 5:Viết lệnh cho phép người dùng tạo account 
        Account account = createAccount();

        // Question 6: Viết lệnh cho phép người dùng tạo department (viết thành method)
        Department department = createDepartment();

        // Question 7: Nhập số chẵn từ console
        inputEvenNumber();

        Account[] accounts = {account};
        Department[] departments = {department};
        Group[] groups = {
                new Group(1, "Java Fresher", account, LocalDate.now()),
                new Group(2, "C# Fresher", account, LocalDate.now())
        };
        GroupAccount[] groupAccounts = new GroupAccount[0];

        // question 8-11: Thêm account vào group, thêm account vào group ngẫu nhiên, tạo menu
        runMenu(accounts, departments, groups, groupAccounts);
    }

    private static void inputThreeIntegers() {
        System.out.println("\nQuestion 1: Nhập 3 số nguyên");
        int first = readInt("Số thứ 1: ");
        int second = readInt("Số thứ 2: ");
        int third = readInt("Số thứ 3: ");
        System.out.printf("Bạn đã nhập: %d, %d, %d%n", first, second, third);
    }

    private static void inputTwoDoubles() {
        System.out.println("\nQuestion 2: Nhập 2 số thực");
        double first = readDouble("Số thực thứ 1: ");
        double second = readDouble("Số thực thứ 2: ");
        System.out.printf("Bạn đã nhập: %.2f, %.2f%n", first, second);
    }

    private static void inputFullName() {
        System.out.println("\nQuestion 3: Nhập họ và tên");
        System.out.print("Họ và tên: ");
        System.out.println("Họ và tên vừa nhập: " + readLine());
    }

    private static void inputBirthday() {
        System.out.println("\nQuestion 4: Nhập ngày sinh nhật");
        while (true) {
            System.out.print("Ngày sinh (dd/MM/yyyy): ");
            try {
                LocalDate birthday = LocalDate.parse(readLine(), dateFormatter);
                System.out.println("Ngày sinh: " + birthday.format(dateFormatter));
                return;
            } catch (DateTimeParseException exception) {
                System.out.println("Ngày không hợp lệ, vui lòng nhập lại.");
            }
        }
    }

    public static Account createAccount() {
        System.out.println("\nQuestion 5: Tạo account");
        int id = readInt("Account id: ");

        System.out.print("Email: ");
        String email = readLine();

        System.out.print("Username: ");
        String username = readLine();

        System.out.print("Full name: ");
        String fullName = readLine();

        PositionName positionName = readPosition();
        return new Account(id, email, username, fullName, null,
                new Position(id, positionName), LocalDate.now());
    }

    public static Department createDepartment() {
        System.out.println("\nQuestion 6: Tạo department");
        int id = readInt("Department id: ");
        System.out.print("Department name: ");
        return new Department(id, readLine());
    }

    private static PositionName readPosition() {
        while (true) {
            int choice = readInt("Position (1 Dev, 2 Test, 3 ScrumMaster, 4 PM, 5 PM): ");
            switch (choice) {
                case 1:
                    return PositionName.DEV;
                case 2:
                    return PositionName.TEST;
                case 3:
                    return PositionName.SCRUM_MASTER;
                case 4:
                case 5:
                    return PositionName.PM;
                default:
                    System.out.println("Vui lòng chọn từ 1 đến 5.");
            }
        }
    }

    private static void inputEvenNumber() {
        System.out.println("\nQuestion 7: Nhập số chẵn");
        while (true) {
            int number = readInt("Số chẵn: ");
            if (number % 2 == 0) {
                System.out.println("Số bạn nhập là số chẵn: " + number);
                return;
            }
            System.out.println("Đây không phải số chẵn, vui lòng nhập lại.");
        }
    }

    public static void runMenu(
        Account[] accounts, 
        Department[] departments, 
        Group[] groups,
        GroupAccount[] groupAccounts) {

        while (true) {
            System.out.println("\nQuestion 8-11:");
            System.out.println("1. Tạo account");
            System.out.println("2. Tạo department");
            System.out.println("3. Thêm account vào group");
            System.out.println("4. Thêm account vào group ngẫu nhiên");
            System.out.println("0. Kết thúc");
            int choice = readInt("Mời bạn nhập vào chức năng muốn sử dụng: ");
            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    createDepartment();
                    break;
                case 3:
                    groupAccounts = addAccountToGroup(accounts, groups, groupAccounts);
                    break;
                case 4:
                    groupAccounts = addAccountToRandomGroup(accounts, groups, groupAccounts);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Mời bạn nhập lại");
            }

            System.out.print("Bạn có muốn thực hiện chức năng khác không? (Có/Không): ");
            if (readLine().equalsIgnoreCase("Không")) {
                return;
            }
        }
    }

    public static GroupAccount[] addAccountToGroup(Account[] accounts, Group[] groups,
                                                    GroupAccount[] groupAccounts) {
        System.out.println("Danh sách username:");
        for (Account account : accounts) {
            System.out.println(account.getUsername());
        }

        System.out.print("Nhập username của account: ");
        Account account = findAccount(accounts, readLine());
        if (account == null) {
            System.out.println("Không tìm thấy account.");
            return groupAccounts;
        }

        System.out.println("Danh sách group:");
        for (Group group : groups) {
            System.out.println(group.getGroupName());
        }

        System.out.print("Nhập tên group: ");
        Group group = findGroup(groups, readLine());
        if (group == null) {
            System.out.println("Không tìm thấy group.");
            return groupAccounts;
        }
        
        System.out.println("Đã thêm account vào group " + group.getGroupName());
        return appendGroupAccount(groupAccounts, new GroupAccount(group, account, LocalDate.now()));
    }

    public static GroupAccount[] addAccountToRandomGroup(Account[] accounts, Group[] groups,
                                                          GroupAccount[] groupAccounts) {
        System.out.println("Danh sách username:");
        for (Account account : accounts) {
            System.out.println(account.getUsername());
        }

        System.out.print("Nhập username của account: ");
        Account account = findAccount(accounts, readLine());
        if (account == null || groups.length == 0) {
            System.out.println("Không tìm thấy account hoặc group.");
            return groupAccounts;
        }

        Group group = groups[random.nextInt(groups.length)];
        System.out.println("Group ngẫu nhiên: " + group.getGroupName());
        return appendGroupAccount(groupAccounts, new GroupAccount(group, account, LocalDate.now()));
    }

    private static Account findAccount(Account[] accounts, String username) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    private static Group findGroup(Group[] groups, String groupName) {
        for (Group group : groups) {
            if (group.getGroupName().equals(groupName)) {
                return group;
            }
        }
        return null;
    }

    private static GroupAccount[] appendGroupAccount(GroupAccount[] groupAccounts,
                                                      GroupAccount groupAccount) {
        GroupAccount[] result = new GroupAccount[groupAccounts.length + 1];
        System.arraycopy(groupAccounts, 0, result, 0, groupAccounts.length);
        result[result.length - 1] = groupAccount;
        return result;
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                int value = Integer.parseInt(readLine());
                return value;
            } catch (NumberFormatException exception) {
                System.out.println("Vui lòng nhập số nguyên hợp lệ.");
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Double.parseDouble(readLine().replace(',', '.'));
            } catch (NumberFormatException exception) {
                System.out.println("Vui lòng nhập số thực hợp lệ.");
            }
        }
    }

    private static String readLine() {
        return scanner.nextLine().trim();
    }
}

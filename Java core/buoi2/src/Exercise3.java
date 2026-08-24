import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Exercise3 {
    public static void run() {
    System.out.println("\n===== EXERCISE 3: DATE FORMAT =====");

    Account creator = new Account(
        1,
        "dev1@gmail.com",
        "dev1",
        "Nguyễn Văn A",
        new Department(1, "Sale"),
        new Position(1, enums.PositionName.DEV),
        LocalDate.of(2026, 8, 1));
    Exam exam = new Exam(
        1,
        "EX001",
        "Java Basic",
        new CategoryQuestion(1, "Java"),
        60,
        creator,
        LocalDate.of(2026, 8, 1));

    LocalDate createDate = exam.getCreateDate();

    // Question 1:
    // In ra thông tin Exam thứ 1 và property create date sẽ được format theo định
    // dạng vietnamese
    System.out.println("\nQuestion 1:");
    System.out.println("Exam ID: " + exam.getExamId());
    System.out.println("Code: " + exam.getCode());
    System.out.println("Title: " + exam.getTitle());
    System.out.println("Create date: "+ createDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

    // Question 2:
    // In ra thông tin: Exam đã tạo ngày nào theo định dạng
    // Năm – tháng – ngày – giờ – phút – giây
    System.out.println("\nQuestion 2:");
    System.out.println("Exam được tạo ngày: " + createDate.atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")));

    // Question 3:
    // Chỉ in ra năm của create date property trong Question 2
    System.out.println("\nQuestion 3:");
    System.out.println("Năm tạo Exam: " + createDate.getYear());

    // Question 4:
    // Chỉ in ra tháng và năm của create date property trong Question 2
    System.out.println("\nQuestion 4:");
    System.out.println("Tháng và năm tạo Exam: "
        + createDate.format(DateTimeFormatter.ofPattern("MM-yyyy")));

    // Question 5:
    // Chỉ in ra "MM-DD" của create date trong Question 2
    System.out.println("\nQuestion 5:");
    System.out.println("Ngày và tháng tạo Exam: "
        + createDate.format(DateTimeFormatter.ofPattern("MM-dd")));

    }
}

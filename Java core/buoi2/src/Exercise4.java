import java.time.LocalDate;
import java.util.Random;

public class Exercise4 {
    public static void run() {
        System.out.println("\n===== EXERCISE 4: RANDOM NUMBER =====");
        Random random = new Random();

        // Question 1: In ngẫu nhiên ra 1 số nguyên
        int randomNumber = random.nextInt();
        System.out.println("\nQuestion 1:");
        System.out.println("1 số nguyên ngẫu nhiên: " + randomNumber);

        // Question 2: In ngẫu nhiên ra 1 số thực
        float randomFloat = random.nextFloat();
        System.out.println("\nQuestion 2:");
        System.out.printf("1 Số thực ngẫu nhiên %f: \n" , randomFloat);
 
        // Question 3: Khai báo 1 array bao gồm các tên của các bạn trong lớp, sau đó in ngẫu nhiên ra tên của 1 bạn
        String[] studentNames = {"Nguyễn Văn A", "Trần Thị B", "Lê Văn C", "Phạm Thị D"};
        System.out.println("\nQuestion 3:");
        System.out.println("Tên học sinh ngẫu nhiên: "
            + studentNames[random.nextInt(studentNames.length)]);

        // Question 4: Lấy ngẫu nhiên 1 ngày trong khoảng thời gian 24-07-1995 tới ngày 20-12- 1995
        LocalDate startDate = LocalDate.of(1995, 7, 24);
        LocalDate endDate = LocalDate.of(1995, 12, 20);
        System.out.println("\nQuestion 4:");
        System.out.println("Ngày ngẫu nhiên từ 24-07-1995 đến 20-12-1995: "
            + randomDateBetween(startDate, endDate, random));

        // Question 5: Lấy ngẫu nhiên 1 ngày trong khoảng thời gian 1 năm trở lại đây
        LocalDate today = LocalDate.now();
        System.out.println("\nQuestion 5:");
        System.out.println("Ngày ngẫu nhiên trong một năm trở lại đây: "
            + randomDateBetween(today.minusYears(1), today, random));

        // Question 6: Lấy ngẫu nhiên 1 ngày trong quá khứ.
        System.out.println("\nQuestion 6:");
        System.out.println("Ngày ngẫu nhiên trong quá khứ: "
            + randomDateBetween(LocalDate.of(1970, 1, 1), today.minusDays(1), random));

        // Question 7: Lấy ngẫu nhiên 1 số có 3 chữ số.
        System.out.println("\nQuestion 7:");
        System.out.println("Số ngẫu nhiên có 3 chữ số: " + (100 + random.nextInt(900)));
        }

        private static LocalDate randomDateBetween(LocalDate startDate, LocalDate endDate,
                            Random random) {
        long daysBetween = endDate.toEpochDay() - startDate.toEpochDay();
        return startDate.plusDays(random.nextInt((int) (daysBetween + 1)));
    }
}

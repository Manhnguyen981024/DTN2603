import java.util.Random;

public class Exercise1 {
    public static void run() {
        System.out.println("\n===== EXERCISE 1: DATATYPE CASTING =====");

        question1(5240.5f, 10970.055f);

        int randomNumber = question2(100000);

        question3(randomNumber, 100);
        
        System.out.println("\nQuestion 4:");
        System.out.println("Thương của 10 và 3 là: " + question4(10, 3));
    }

    private static void question1(float salaryAccount1, float salaryAccount2) {
        System.out.println("\nQuestion 1:");
        int roundedSalaryAccount1 = (int) salaryAccount1;
        int roundedSalaryAccount2 = (int) salaryAccount2;

        System.out.println("Lương Account 1 sau khi ép kiểu: " + roundedSalaryAccount1);
        System.out.println("Lương Account 2 sau khi ép kiểu: " + roundedSalaryAccount2);
    }

    private static int question2(int randomBound) {
        System.out.println("\nQuestion 2:");
        int randomNumber = new Random().nextInt(randomBound);
        System.out.printf("Số ngẫu nhiên có 5 chữ số: %05d%n", randomNumber);
        return randomNumber;
    }

    private static void question3(int number, int divisor) {
        System.out.println("\nQuestion 3:");
        System.out.println("Hai số cuối: " + number % divisor);
    }

    private static int question4(int a, int b) {
        return a / b;
    }
}

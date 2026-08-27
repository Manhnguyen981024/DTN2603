public class Exercise3 {
    public static void run() {
        System.out.println("\n===== EXERCISE 3: CONVERT DATATYPE =====");

        question1(5000);
        question2("1234567");
        question3("1234567");
    }

    private static void question1(Integer salary) {
        System.out.println("\nQuestion 1:");
        float salaryAsFloat = salary.floatValue();

        System.out.printf("Lương sau khi convert sang float: %.2f%n", salaryAsFloat);
    }

    private static void question2(String numberAsString) {
        System.out.println("\nQuestion 2:");
        int number = Integer.parseInt(numberAsString);

        System.out.println("Số int sau khi convert từ String: " + number);
    }

    private static void question3(String numberAsString) {
        System.out.println("\nQuestion 3:");
        Integer numberAsInteger = Integer.valueOf(numberAsString);
        int number = numberAsInteger.intValue();

        System.out.println("Số int sau khi convert từ Integer: " + number);
    }
}

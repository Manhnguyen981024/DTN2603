import java.time.LocalDate;

public class Exercise2 {
    public static void run() {
        System.out.println("\n===== EXERCISE 2: Default value =====");
        question1(5, "Email ", "User name ", "Full name ", LocalDate.now());
    }

    private static Account[] question1(int accountCount, String emailPrefix,
            String usernamePrefix, String fullNamePrefix, LocalDate createDate) {
        System.out.println("\nQuestion 1:");
        Account[] accounts = new Account[accountCount];

        for (int index = 0; index < accounts.length; index++) {
            int accountNumber = index + 1;
            accounts[index] = new Account(
                    accountNumber,
                    emailPrefix + accountNumber,
                    usernamePrefix + accountNumber,
                    fullNamePrefix + accountNumber,
                    createDate);
        }

        return accounts;
    }
}

import java.util.Scanner;

public class Exercise4 {
    private static final Scanner scanner = new Scanner(System.in);

    public static void run() {
        System.out.println("\n===== EXERCISE 4: STRING =====");

        question1(readLine("Nhập xâu thứ nhất: "));
        question2(readLine("Nhập xâu s1: "), readLine("Nhập xâu s2: "));
        question3(readLine("Nhập tên: "));
        question4(readLine("Nhập tên: "));
        question5(readLine("Nhập họ: "), readLine("Nhập tên: "));
        question6(readLine("Nhập họ và tên đầy đủ: "));
        question7(readLine("Nhập họ và tên cần chuẩn hóa: "));

        Group[] groups = {
            new Group(1, "Java", null, null),
            new Group(2, "Java Fresher", null, null),
            new Group(3, "C# Fresher", null, null),
            new Group(4, "Java Advanced", null, null),
            new Group(5, "Tester", null, null)
        };
        question8(groups, "Java");
        question9(groups, "Java");
        question10(readLine("Nhập chuỗi thứ nhất: "), readLine("Nhập chuỗi thứ hai: "));
        question11(readLine("Nhập chuỗi: "), 'a');
        question12(readLine("Nhập chuỗi cần đảo ngược: "));
        question13(readLine("Nhập chuỗi: "));
        question14(readLine("Nhập chuỗi: "), 'e', '*');
        question15(readLine("Nhập chuỗi cần đảo ngược theo từ: "));
        question16(readLine("Nhập chuỗi: "), readInt("Nhập n: "));
    }

    private static void question1(String text) {
        System.out.println("\nQuestion 1:");
        String trimmedText = text == null ? "" : text.trim();
        int wordCount = trimmedText.isEmpty() ? 0 : trimmedText.split("\\s+").length;
        System.out.println("Số lượng từ: " + wordCount);
    }

    private static void question2(String firstText, String secondText) {
        System.out.println("\nQuestion 2:");
        System.out.println("Xâu sau khi nối: " + firstText.concat(secondText));
    }

    private static void question3(String name) {
        System.out.println("\nQuestion 3:");
        System.out.println("Tên sau khi viết hoa chữ cái đầu: " + capitalizeFirstLetter(name));
    }

    private static void question4(String name) {
        System.out.println("\nQuestion 4:");
        for (int index = 0; index < name.length(); index++) {
            System.out.println("Ký tự thứ " + (index + 1) + " là: " + name.charAt(index));
        }
    }

    private static void question5(String familyName, String givenName) {
        System.out.println("\nQuestion 5:");
        System.out.println("Họ và tên đầy đủ: " + familyName + " " + givenName);
    }

    private static void question6(String fullName) {
        System.out.println("\nQuestion 6:");
        String[] nameParts = normalizeSpaces(fullName).split(" ");
        System.out.println("Họ là: " + nameParts[0]);
        if (nameParts.length > 2) {
            String middleName = ""; //String.join(" ", Arrays.copyOfRange(nameParts, 1, nameParts.length - 1));
            for (int i = 1; i < nameParts.length - 1; i++){
                middleName += nameParts[i] + " ";
            }
            System.out.println("Tên đệm là: " + middleName);
        } else {
            System.out.println("Không có tên đệm");
        }
        System.out.println("Tên là: " + nameParts[nameParts.length - 1]);
    }

    private static void question7(String fullName) {
        System.out.println("\nQuestion 7:");
        String normalizedName = normalizeSpaces(fullName);
        StringBuilder capitalizedName = new StringBuilder();
        for (String word : normalizedName.split(" ")) {
            if (capitalizedName.length() > 0) {
                capitalizedName.append(' ');
            }
            capitalizedName.append(capitalizeFirstLetter(word));
        }
        System.out.println("Họ và tên sau khi chuẩn hóa: " + capitalizedName);
    }

    private static void question8(Group[] groups, String keyword) {
        System.out.println("\nQuestion 8:");
        printGroupsByName(groups, keyword, "contains");
    }

    private static void question9(Group[] groups, String groupName) {
        System.out.println("\nQuestion 9:");
        printGroupsByName(groups, groupName, "equals");
    }

    private static void printGroupsByName(Group[] groups, String name, String comparisonType) {
        for (Group group : groups) {
            if (isMatch(group.getGroupName(), name, comparisonType)) {
                System.out.println(group.getGroupName());
            }
        }
    }

    private static boolean isMatch(String currentName, String expectedName, String comparisonType) {
        if ("contains".equals(comparisonType)) {
            return currentName.contains(expectedName);
        }
        if ("equals".equals(comparisonType)) {
            return currentName.equals(expectedName);
        }
        return false;
    }

    private static void question10(String firstText, String secondText) {
        System.out.println("\nQuestion 10:");
        System.out.println(reverse(firstText).equals(secondText) ? "OK" : "KO");
    }

    private static void question11(String text, char character) {
        System.out.println("\nQuestion 11:");
        int count = 0;
        for (int index = 0; index < text.length(); index++) {
            if (text.charAt(index) == character) {
                count++;
            }
        }
        System.out.println("Số lần xuất hiện ký tự '" + character + "': " + count);
    }

    private static void question12(String text) {
        System.out.println("\nQuestion 12:");
        System.out.println(reverse(text));
    }

    private static void question13(String text) {
        System.out.println("\nQuestion 13:");
        boolean containsNoDigit = text != null;
        if (containsNoDigit) {
            for (int index = 0; index < text.length(); index++) {
                if (Character.isDigit(text.charAt(index))) {
                    containsNoDigit = false;
                    break;
                }
            }
        }
        System.out.println(containsNoDigit);
    }

    private static void question14(String text, char oldCharacter, char newCharacter) {
        System.out.println("\nQuestion 14:");
        StringBuilder result = new StringBuilder();
        text.replaceAll("a", "b");
        for (int index = 0; index < text.length(); index++) {
            result.append(text.charAt(index) == oldCharacter ? newCharacter : text.charAt(index));
        }
        System.out.println(result);
    }

    private static void question15(String text) {
        System.out.println("\nQuestion 15:");
        String[] words = normalizeSpaces(text).split(" ");
        for (int index = words.length - 1; index >= 0; index--) {
            System.out.print(words[index]);
            if (index > 0) {
                System.out.print(' ');
            }
        }
        System.out.println();
    }

    private static void question16(String text, int partLength) {
        System.out.println("\nQuestion 16:");
        if (partLength <= 0 || text.length() % partLength != 0) {
            System.out.println("KO");
            return;
        }
        for (int index = 0; index < text.length(); index += partLength) {
            System.out.println(text.substring(index, index + partLength));
        }
    }

    private static String capitalizeFirstLetter(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return Character.toUpperCase(text.charAt(0)) + text.substring(1).toLowerCase();
    }

    private static String normalizeSpaces(String text) {
        return text == null ? "" : text.trim().replaceAll("\\s+", " ");
    }

    private static String reverse(String text) {
        //StringBuilder result = new StringBuilder();
        char[] charArr = new char[text.length()];
        for (int index = text.length() - 1; index >= 0; index--) {
            charArr[text.length() - 1 - index] = text.charAt(index);
        }
        return String.valueOf(charArr);
    }

    private static String readLine(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    private static int readInt(String message) {
        while (true) {
            try {
                return Integer.parseInt(readLine(message).trim());
            } catch (NumberFormatException exception) {
                System.out.println("Vui lòng nhập số nguyên hợp lệ.");
            }
        }
    }
}

import java.time.LocalDate;

public class Program {

    public static void main(String[] args) {

        // 1. Department
        Department department1 = new Department(1, "Sale");
        Department department2 = new Department(2, "Marketing");
        Department department3 = new Department(3, "IT");

        System.out.println("Department:");
        System.out.println(department1.getDepartmentName());
        System.out.println(department2.getDepartmentName());
        System.out.println(department3.getDepartmentName());

        // 2. Position
        Position position1 = new Position(1, "Developer");
        Position position2 = new Position(2, "Tester");
        Position position3 = new Position(3, "Project Manager");

        System.out.println("\nPosition:");
        System.out.println(position1.getPositionName());
        System.out.println(position2.getPositionName());
        System.out.println(position3.getPositionName());

        // 3. Account
        Account account1 = new Account(
                1, "dev1@gmail.com", "dev1", "Nguyen Van A",
                department3, position1, LocalDate.of(2026, 8, 1));

        Account account2 = new Account(
                2, "tester1@gmail.com", "tester1", "Nguyen Van B",
                department3, position2, LocalDate.of(2026, 8, 2));

        Account account3 = new Account(
                3, "pm1@gmail.com", "pm1", "Nguyen Van C",
                department3, position3, LocalDate.of(2026, 8, 3));

        System.out.println("\nAccount:");
        System.out.println(account1.getUsername());
        System.out.println(account2.getUsername());
        System.out.println(account3.getUsername());

        // 4. Group
        Group group1 = new Group(1, "Java Team", account1, LocalDate.of(2026, 8, 1));
        Group group2 = new Group(2, "Testing Team", account2, LocalDate.of(2026, 8, 2));
        Group group3 = new Group(3, "Project Team", account3, LocalDate.of(2026, 8, 3));

        System.out.println("\nGroup:");
        System.out.println(group1.getGroupName());
        System.out.println(group2.getGroupName());
        System.out.println(group3.getGroupName());

        // 5. GroupAccount
        GroupAccount groupAccount1 = new GroupAccount(group1, account1, LocalDate.of(2026, 8, 1));
        GroupAccount groupAccount2 = new GroupAccount(group1, account2, LocalDate.of(2026, 8, 2));
        GroupAccount groupAccount3 = new GroupAccount(group2, account3, LocalDate.of(2026, 8, 3));

        System.out.println("\nGroupAccount:");
        System.out.println(groupAccount1.getGroup().getGroupName());
        System.out.println(groupAccount2.getGroup().getGroupName());
        System.out.println(groupAccount3.getGroup().getGroupName());

        // 6. TypeQuestion
        TypeQuestion typeQuestion1 = new TypeQuestion(1, "Essay");
        TypeQuestion typeQuestion2 = new TypeQuestion(2, "Multiple-Choice");
        TypeQuestion typeQuestion3 = new TypeQuestion(3, "Essay");

        System.out.println("\nTypeQuestion:");
        System.out.println(typeQuestion1.getTypeName());
        System.out.println(typeQuestion2.getTypeName());
        System.out.println(typeQuestion3.getTypeName());

        // 7. CategoryQuestion
        CategoryQuestion category1 = new CategoryQuestion(1, "Java");
        CategoryQuestion category2 = new CategoryQuestion(2, ".NET");
        CategoryQuestion category3 = new CategoryQuestion(3, "SQL");

        System.out.println("\nCategoryQuestion:");
        System.out.println(category1.getCategoryName());
        System.out.println(category2.getCategoryName());
        System.out.println(category3.getCategoryName());

        // 8. Question
        Question question1 = new Question(
                1, "Java là gì?", category1, typeQuestion1, account1, LocalDate.of(2026, 8, 1));

        Question question2 = new Question(
                2, "OOP là gì?", category1, typeQuestion2, account1, LocalDate.of(2026, 8, 2));

        Question question3 = new Question(
                3, "SQL JOIN là gì?", category3, typeQuestion1, account2, LocalDate.of(2026, 8, 3));

        System.out.println("\nQuestion:");
        System.out.println(question1.getContent());
        System.out.println(question2.getContent());
        System.out.println(question3.getContent());

        // 9. Answer
        Answer answer1 = new Answer(
                1, "Java là ngôn ngữ lập trình", question1, true);

        Answer answer2 = new Answer(
                2, "Java là database", question1, false);

        Answer answer3 = new Answer(
                3, "Java hỗ trợ OOP", question1, true);

        System.out.println("\nAnswer:");
        System.out.println(answer1.getContent());
        System.out.println(answer2.getContent());
        System.out.println(answer3.getContent());

        // 10. Exam
        Exam exam1 = new Exam(
                1, "EX001", "Java Basic", category1, 60, account1,
                LocalDate.of(2026, 8, 1));

        Exam exam2 = new Exam(
                2, "EX002", "Java OOP", category1, 90, account1,
                LocalDate.of(2026, 8, 2));

        Exam exam3 = new Exam(
                3, "EX003", "SQL Basic", category3, 60, account2,
                LocalDate.of(2026, 8, 3));

        System.out.println("\nExam:");
        System.out.println(exam1.getTitle());
        System.out.println(exam2.getTitle());
        System.out.println(exam3.getTitle());

        // 11. ExamQuestion
        ExamQuestion examQuestion1 = new ExamQuestion(exam1, question1);
        ExamQuestion examQuestion2 = new ExamQuestion(exam1, question2);
        ExamQuestion examQuestion3 = new ExamQuestion(exam2, question3);

        System.out.println("\nExamQuestion:");
        System.out.println(examQuestion1.getQuestion().getContent());
        System.out.println(examQuestion2.getQuestion().getContent());
        System.out.println(examQuestion3.getQuestion().getContent());
    }
}

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
                3, 1, LocalDate.of(2026, 8, 1));

        Account account2 = new Account(
                2, "tester1@gmail.com", "tester1", "Nguyen Van B",
                3, 2, LocalDate.of(2026, 8, 2));

        Account account3 = new Account(
                3, "pm1@gmail.com", "pm1", "Nguyen Van C",
                3, 3, LocalDate.of(2026, 8, 3));

        System.out.println("\nAccount:");
        System.out.println(account1.getUsername());
        System.out.println(account2.getUsername());
        System.out.println(account3.getUsername());

        // 4. Group
        Group group1 = new Group(1, "Java Team", 1, LocalDate.of(2026, 8, 1));
        Group group2 = new Group(2, "Testing Team", 2, LocalDate.of(2026, 8, 2));
        Group group3 = new Group(3, "Project Team", 3, LocalDate.of(2026, 8, 3));

        System.out.println("\nGroup:");
        System.out.println(group1.getGroupName());
        System.out.println(group2.getGroupName());
        System.out.println(group3.getGroupName());

        // 5. GroupAccount
        GroupAccount groupAccount1 = new GroupAccount(1, 1, LocalDate.of(2026, 8, 1));
        GroupAccount groupAccount2 = new GroupAccount(1, 2, LocalDate.of(2026, 8, 2));
        GroupAccount groupAccount3 = new GroupAccount(2, 3, LocalDate.of(2026, 8, 3));

        System.out.println("\nGroupAccount:");
        System.out.println(groupAccount1.getGroupId());
        System.out.println(groupAccount2.getGroupId());
        System.out.println(groupAccount3.getGroupId());

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
                1, "Java là gì?", 1, 1, 1, LocalDate.of(2026, 8, 1));

        Question question2 = new Question(
                2, "OOP là gì?", 1, 2, 1, LocalDate.of(2026, 8, 2));

        Question question3 = new Question(
                3, "SQL JOIN là gì?", 3, 1, 2, LocalDate.of(2026, 8, 3));

        System.out.println("\nQuestion:");
        System.out.println(question1.getContent());
        System.out.println(question2.getContent());
        System.out.println(question3.getContent());

        // 9. Answer
        Answer answer1 = new Answer(
                1, "Java là ngôn ngữ lập trình", 1, true);

        Answer answer2 = new Answer(
                2, "Java là database", 1, false);

        Answer answer3 = new Answer(
                3, "Java hỗ trợ OOP", 1, true);

        System.out.println("\nAnswer:");
        System.out.println(answer1.getContent());
        System.out.println(answer2.getContent());
        System.out.println(answer3.getContent());

        // 10. Exam
        Exam exam1 = new Exam(
                1, "EX001", "Java Basic", 1, 60, 1,
                LocalDate.of(2026, 8, 1));

        Exam exam2 = new Exam(
                2, "EX002", "Java OOP", 1, 90, 1,
                LocalDate.of(2026, 8, 2));

        Exam exam3 = new Exam(
                3, "EX003", "SQL Basic", 3, 60, 2,
                LocalDate.of(2026, 8, 3));

        System.out.println("\nExam:");
        System.out.println(exam1.getTitle());
        System.out.println(exam2.getTitle());
        System.out.println(exam3.getTitle());

        // 11. ExamQuestion
        ExamQuestion examQuestion1 = new ExamQuestion(1, 1);
        ExamQuestion examQuestion2 = new ExamQuestion(1, 2);
        ExamQuestion examQuestion3 = new ExamQuestion(2, 3);

        System.out.println("\nExamQuestion:");
        System.out.println(examQuestion1.getQuestionId());
        System.out.println(examQuestion2.getQuestionId());
        System.out.println(examQuestion3.getQuestionId());


        int[] scores = {90, 80, 70, 60};

        for (int score : scores) {
            System.out.println(score);
        }

        Integer a = 10000;
        Integer b = 10000;
        System.out.println(a == b);
    }
}

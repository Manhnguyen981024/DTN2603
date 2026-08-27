public class Exercise5 {
    public static void run() {
        System.out.println("\n===== EXERCISE 5: Object’s Method =====");

        Department department1 = new Department(1, "Phòng A", "Địa chỉ A");
        Department department2 = new Department(2, "Phòng B", "Địa chỉ B");
        
        System.out.println("\nQuestion 1:");
        question1(department1);
        question2(new Department[] {department1, department2});
        question3(department1);

        System.out.printf("\nQuestion 4:");
        question4(department1, "Phòng A");
        question5(department1, department2);

        Department[] departmentsForQuestion6 = createDepartments();
        question6(departmentsForQuestion6);

        Department[] departmentsForQuestion7 = createDepartments();
        question7(departmentsForQuestion7);
    }

    private static void question1(Department department) {
        System.out.println(department.toString());
    }

    private static void question2(Department[] departments) {
        System.out.println("\nQuestion 2:");
        for (Department department : departments) {
            question1(department);
        }
    }

    private static void question3(Department department) {
        System.out.println("\nQuestion 3:");
        System.out.println("Địa chỉ phòng ban: " + department.getAddress());
    }

    private static void question4(Department department, String departmentName) {
        System.out.printf("Kiểm tra xem phòng ban %s có tên '%s' hay không: \n", department.getDepartmentName(), departmentName);
        System.out.println(department.getDepartmentName().equals(departmentName) ? "Đúng rồi" : "Sai rồi");
    }

    private static void question5(Department firstDepartment, Department secondDepartment) {
        System.out.println("\nQuestion 5:");
        question4(firstDepartment, secondDepartment.getDepartmentName());
    }

    private static void question6(Department[] departments) {
        System.out.println("\nQuestion 6:");
        sort(departments, "asc", false);
        printDepartmentNames(departments);
    }

    private static void question7(Department[] departments) {
        System.out.println("\nQuestion 7:");
        sort(departments, "name", true);
        printDepartmentNames(departments);
    }

    private static Department[] createDepartments() {
        return new Department[] {
                new Department(1, "Sale", "Địa chỉ Sale"),
                new Department(2, "Accounting", "Địa chỉ Accounting"),
                new Department(3, "Waiting room", "Địa chỉ Waiting room"),
                new Department(4, "Boss of director", "Địa chỉ Boss of director"),
                new Department(5, "Marketing", "Địa chỉ Marketing")
        };
    }

    private static void sort(Department[] departments, String sortBy, boolean isCompareWithIgnoreCase) {
        for (int i = 0; i < departments.length - 1; i++) {

            for (int j = 0; j < departments.length - i - 1; j++) {

                String currentName = getNameToCompareString(departments[j].getDepartmentName(), sortBy);
                String nextName = getNameToCompareString(departments[j + 1].getDepartmentName(), sortBy);

                if (isGreater(currentName, nextName, isCompareWithIgnoreCase)) {
                    Department temp = departments[j];
                    departments[j] = departments[j + 1];
                    departments[j + 1] = temp;
                }
            }
        }
    }

    private static boolean isGreater(String currString, String nextString, boolean isIgnoreCase){
        if (isIgnoreCase) return currString.compareToIgnoreCase(nextString) > 0;
        return currString.compareTo(nextString) > 0;
    }

    private static String getNameToCompareString(String nameString, String sortBy){
        if ("name".equals(sortBy)) {
            String[] nameArr = nameString.split(" ");
            return nameArr[nameArr.length - 1];
        }
        return nameString;
    }

    private static void printDepartmentNames(Department[] departments) {
        for (Department department : departments) {
            System.out.println(department.getDepartmentName());
        }
    }
}

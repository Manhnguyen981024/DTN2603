package com.dtn2603.frontend;

import com.dtn2603.constant.AppConstants;
import com.dtn2603.controller.DepartmentController;
import com.dtn2603.entity.Department;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
public class DepartmentConsole {
    private final DepartmentController departmentController;
    private final InputReader inputReader;

    public void menu(){
        boolean isRunning = true;
        while(isRunning){
            System.out.println("===== Department Management =====");
            System.out.println("1. View all departments");
            System.out.println("2. Import department from CSV file");
            System.out.println("3. Exit program");

            String option = inputReader.readString("Enter your choice:");
            switch(option){
                case "1":
                    this.viewDepartments();
                    break;
                case "2":
                    this.importDepartmentFromCSV();
                    break;
                case "3":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private void viewDepartments() {
        List<Department> departments = departmentController.getAllDepartment();
        ConsoleView.showDepartmentsToConsole(departments);
    }

    
    private void importDepartmentFromCSV(){
        String fileName = inputReader.readString("Enter your file path: ");
        Map<String, Integer> results = departmentController.importDepartmentFromCSV(fileName);
        if (Objects.nonNull(results)){
            System.out.println("==== Import CSV Report ==== ");
            int errors = results.getOrDefault("errorCount", 0);
            System.out.printf("- Total counts: %d\n", results.getOrDefault("totalCount", 0));
            System.out.printf("- Success counts: %d\n", results.getOrDefault("successCount", 0));
            System.out.printf("- Error counts: %d\n", errors);
            if (errors > 0){
                System.out.printf("Please access the path: %s to see the error logs\n " , AppConstants.IMPORT_ACCOUNT_ERROR_LOG);
            }
        } else {
            System.err.println("Import failed! Please try again");
        }
    }
}

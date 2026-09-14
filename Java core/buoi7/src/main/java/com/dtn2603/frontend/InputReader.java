package com.dtn2603.frontend;

import com.dtn2603.entity.Department;
import com.dtn2603.entity.Position;
import com.dtn2603.entity.enums.Gender;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class InputReader {
    private final Scanner scanner;

    public int readValidDepartment(List<Department> departments) {
        Integer departmentId;
        while(true){
            departmentId = readInt("Enter your department id: ");
            Integer finalDepartmentId = departmentId;
            if(departments.stream()
                    .anyMatch(department -> department.getDepartmentId() == finalDepartmentId)){
                break;
            }
            System.out.println("Please enter a valid department id");
        }
        return departmentId;
    }

    public int readValidPosition(List<Position> positions){
        Integer positionId;
        while(true){
            positionId = readInt("Enter your position id");
            Integer finalPositionId = positionId;

            if(positions.stream().anyMatch(position -> position.getPositionId() == finalPositionId)){
                break;
            }
            System.out.println("Please enter a valid position id: ");
        }
        return positionId;
    }

    public Gender readValidGender(Scanner scanner) {
        while (true) {
            String genderM = readString("Enter your gender M, F or U");
            try {
                return Gender.valueOf(genderM.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid gender");
            }
        }
    }

    public int readInt(String message){
        System.out.println(message);
        while(true){
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
            }
        }
    }

    public String readString(String message){
        System.out.println(message);
        return scanner.nextLine().trim();
    }
}

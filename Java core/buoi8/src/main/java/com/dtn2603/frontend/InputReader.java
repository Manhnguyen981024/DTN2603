package com.dtn2603.frontend;

import com.dtn2603.entity.Department;
import com.dtn2603.entity.Position;
import com.dtn2603.entity.enums.Gender;
import com.dtn2603.utils.ValidationUtils;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;
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
                    .anyMatch(department -> Objects.equals(department.getDepartmentId(), finalDepartmentId))){
                break;
            }
            System.err.println("Please enter a valid department id");
        }
        return departmentId;
    }

    public int readValidPosition(List<Position> positions){
        Integer positionId;
        while(true){
            positionId = readInt("Enter your position id");
            Integer finalPositionId = positionId;

            if(positions.stream().anyMatch(position -> Objects.equals(position.getPositionId(), finalPositionId))){
                break;
            }
            System.err.println("Please enter a valid position id: ");
        }
        return positionId;
    }

    public Gender readValidGender() {
        while (true) {
            String genderM = readString("Enter your gender M, F or U");
            try {
                return Gender.valueOf(genderM.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid gender");
            }
        }
    }

    public int readInt(String message){
        System.out.println(message);
        while(true){
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Invalid input");
            }
        }
    }

    public String readString(String message){
        System.out.println(message);
        return scanner.nextLine().trim();
    }

    public String readStringWithLengthBetween(String message, int min, int max){
        String temp;
        while(true){
            temp = readString(message);
            if (ValidationUtils.isValidLength(temp, min, max)){
                break;
            }
            System.err.printf("Input length should be between %d and %d\n", min, max);
        }
        return temp;
    }

    public String readEmail(String message){
        String email;
        while (true) {
            email = this.readString(message);
            if (!ValidationUtils.isValidLength(email, 6, 100)){
                System.err.println("Invalid length, email must be between 6 and 100 characters");
                continue;
            }
            if(!ValidationUtils.isValidEmail(email)){
                System.err.println("Invalid email format");
                continue;
            }
            break;
        }
        return email;
    }
}

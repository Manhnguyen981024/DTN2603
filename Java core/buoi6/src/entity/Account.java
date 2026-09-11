package entity;

import java.time.LocalDate;

import entity.enums.Gender;

public class Account {
    private int accountId;
    private String email;
    private String username;
    private String fullName;
    private Department department;
    private Position position;
    private LocalDate createDate;
    private Gender gender;

    public Account() {}

    public Account(
            int accountId,
            String email,
            String username,
            String fullName,
            Gender gender,
            Department department,
            Position position,
            LocalDate createDate) {

        this.accountId = accountId;
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.gender = gender;
        this.department = department;
        this.position = position;
        this.createDate = createDate;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public Gender getGender() {
        return gender;
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        String accountString = String.format("|%19s |%19s |%19s |%19s |%19s |%19s |%19s |%19s |%19s |", accountId, email, username, fullName,
                (department != null ? department.getDepartmentId() : "null"),
                (department != null ? department.getDepartmentName() : "null"),
                (position != null ? position.getPositionId() : "null"),
                (position != null ? position.getPositionName() : "null"),
                createDate);

        return accountString;
    }
}

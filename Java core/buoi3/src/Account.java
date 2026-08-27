import java.time.LocalDate;

public class Account {
    private int accountId;
    private String email;
    private String username;
    private String fullName;
    private Department department;
    private Position position;
    private LocalDate createDate;

    public Account(
            int accountId,
            String email,
            String username,
            String fullName,
            Department department,
            Position position,
            LocalDate createDate) {

        this.accountId = accountId;
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.department = department;
        this.position = position;
        this.createDate = createDate;
    }

     public Account(
            int accountId,
            String email,
            String username,
            String fullName,
            LocalDate createDate) {

        this.accountId = accountId;
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.createDate = createDate;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public Department getDepartment() {
        return department;
    }

    public Position getPosition() {
        return position;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }
}

import java.time.LocalDate;

public class Account {

    private int accountId;
    private String email;
    private String username;
    private String fullName;
    private int departmentId;
    private int positionId;
    private LocalDate createDate;

    public Account(
            int accountId,
            String email,
            String username,
            String fullName,
            int departmentId,
            int positionId,
            LocalDate createDate) {

        this.accountId = accountId;
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.departmentId = departmentId;
        this.positionId = positionId;
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

    public int getDepartmentId() {
        return departmentId;
    }

    public int getPositionId() {
        return positionId;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }
}

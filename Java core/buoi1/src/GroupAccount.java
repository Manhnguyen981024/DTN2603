import java.time.LocalDate;

public class GroupAccount {

    private int groupId;
    private int accountId;
    private LocalDate joinDate;

    public GroupAccount(int groupId, int accountId, LocalDate joinDate) {
        this.groupId = groupId;
        this.accountId = accountId;
        this.joinDate = joinDate;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getAccountId() {
        return accountId;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }
}

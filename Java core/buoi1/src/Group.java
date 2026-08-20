import java.time.LocalDate;

public class Group {

    private int groupId;
    private String groupName;
    private int creatorId;
    private LocalDate createDate;

    public Group(int groupId, String groupName, int creatorId, LocalDate createDate) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.creatorId = creatorId;
        this.createDate = createDate;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }
}

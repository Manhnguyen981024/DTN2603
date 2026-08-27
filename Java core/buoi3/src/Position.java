import enums.PositionName;

public class Position {
    private int positionId;
    private PositionName positionName;

    public Position(int positionId, PositionName positionName) {
        this.positionId = positionId;
        this.positionName = positionName;
    }

    public int getPositionId() {
        return positionId;
    }

    public PositionName getPositionName() {
        return positionName;
    }
}

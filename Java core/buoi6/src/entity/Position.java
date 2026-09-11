package entity;

// import entity.enums.PositionName;

public class Position {
    private Integer positionId;
    // private PositionName positionName;
    private String positionNameString;

    public Position() {
    }

    public Position(Integer positionId) {
        this.positionId = positionId;
    }


    // public Position(Integer positionId, PositionName positionName) {
    //     this.positionId = positionId;
    //     this.positionName = positionName;
    // }

    public Position(Integer positionId, String positionNameString) {
        this.positionId = positionId;
        this.positionNameString = positionNameString;
    }

    public Integer getPositionId() {
        return positionId;
    }

    // public PositionName getPositionName() {
    //     return positionName;
    // }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    // public void setPositionName(PositionName positionName) {
    //     this.positionName = positionName;
    // }

    public String getPositionName(){
        return this.positionNameString;
    }

    public void setPositionName(String positionNameString) {
        this.positionNameString = positionNameString;
    }

    @Override
    public String toString() {
        return String.format("|%19s |%19s |", positionId, positionNameString);
    }
}

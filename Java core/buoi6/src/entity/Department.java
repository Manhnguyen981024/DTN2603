package entity;

public class Department {
    private Integer departmentId;
    private String departmentName;

    public Department() {
    }

    public Department(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Department(Integer departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

     public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return String.format("|%19s |%19s |", departmentId, departmentName);
    }
}

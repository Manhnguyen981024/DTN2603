public class Department {
    private int departmentId;
    private String departmentName;
    private String address;

    public Department(int departmentId, String departmentName) {
        this(departmentId, departmentName, null);
    }

    public Department(int departmentId, String departmentName, String address) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.address = address;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

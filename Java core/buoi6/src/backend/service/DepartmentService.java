package backend.service;

import java.util.Comparator;
import java.util.List;

import backend.repository.IQLDepartment;
import entity.Department;

public class DepartmentService {
    private final IQLDepartment qlDepartment;

    public DepartmentService(IQLDepartment qlDepartment) {
        this.qlDepartment = qlDepartment;
    }
    
    public List<Department> findAll(){
        return qlDepartment.findAll()
                            .stream()
                            .sorted(Comparator.comparing(Department::getDepartmentId))
                            .toList();
    }

    public Department createDepartment(Department department) {
        if (department.getDepartmentName() == null || department.getDepartmentName().isEmpty()){
            throw new IllegalArgumentException("Department name cannot be null or empty!");
        }
        return qlDepartment.save(department);
    }

    public Department updateDepartment(Department department) {
        if (department.getDepartmentName() == null || department.getDepartmentName().isEmpty()){
            throw new IllegalArgumentException("Department name cannot be null or empty!");
        }
        return qlDepartment.update(department);
    }

    public void deleteDepartment(Integer id) {
        if (id == null || id <= 0){
            throw new IllegalArgumentException("Department id cannot be null or negative !");
        }
        qlDepartment.deleteById(id);
    }
}

package backend.repository;

import java.util.List;

import entity.Department;

public interface IQLDepartment {
    List<Department> findAll();
    Department findById(Integer id);
    Department save(Department department);
    Department update(Department department);
    void deleteById(Integer id);
}

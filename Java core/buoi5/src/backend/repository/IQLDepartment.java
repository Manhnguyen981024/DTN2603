package backend.repository;

import java.util.List;

import entity.Department;

public interface IQLDepartment {
    List<Department> findAll();
}

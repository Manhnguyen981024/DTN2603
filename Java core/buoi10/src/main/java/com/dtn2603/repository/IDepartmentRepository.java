package com.dtn2603.repository;

import com.dtn2603.entity.Department;

import java.util.List;
import java.util.Set;

public interface IDepartmentRepository {
    List<Department> findAll();
    Department findById(Integer id);
    Set<Integer> findExistingIds(List<Integer> departmentIds);
    int saveAll(List<Department> departments);
}

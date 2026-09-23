package com.dtn2603.service;

import com.dtn2603.entity.Department;

import java.util.List;

public interface IDepartmentService {
    List<Department> getAllDepartment();
    Department getDepartmentById(Integer id);
}

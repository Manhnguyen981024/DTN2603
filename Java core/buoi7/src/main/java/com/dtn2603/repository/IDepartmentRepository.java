package com.dtn2603.repository;

import com.dtn2603.entity.Department;

import java.util.List;

public interface IDepartmentRepository {
    List<Department> findAll();
}

package com.dtn2603.controller;

import com.dtn2603.entity.Department;
import com.dtn2603.exception.DataAccessException;
import com.dtn2603.service.IDepartmentService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class DepartmentController {
    private final IDepartmentService departmentService;

    public List<Department> getAllDepartment() {
        try {
            return departmentService.getAllDepartment();
        } catch (DataAccessException e) {
            System.err.println(e.getMessage());
        }
        return List.of();
    }
}

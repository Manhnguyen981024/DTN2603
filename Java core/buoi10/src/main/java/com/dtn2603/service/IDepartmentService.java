package com.dtn2603.service;

import com.dtn2603.entity.Department;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IDepartmentService extends ICsvImportService<Department> {
    List<Department> getAllDepartment();
    Department getDepartmentById(Integer id);
    Set<Integer> findExistingDepartmentIds(List<Integer> departmentIds);

    default Map<String, Integer> importDepartmentFromCSV(String filename) {
        return importCSV(filename);
    }
}
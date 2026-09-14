package com.dtn2603.service.impl;

import com.dtn2603.entity.Department;
import com.dtn2603.repository.IDepartmentRepository;
import com.dtn2603.service.IDepartmentService;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
public class DepartmentServiceImpl implements IDepartmentService {
    private final IDepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartment() {
        return departmentRepository.findAll()
                .stream().sorted(Comparator.comparing(Department::getDepartmentId)).toList();
    }
}

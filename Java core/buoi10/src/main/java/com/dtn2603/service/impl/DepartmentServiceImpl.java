package com.dtn2603.service.impl;

import com.dtn2603.entity.Department;
import com.dtn2603.repository.IDepartmentRepository;
import com.dtn2603.service.IDepartmentService;
import com.dtn2603.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DepartmentServiceImpl implements IDepartmentService {
    private final int MIN_LENGTH = 2;
    private final int MAX_LENGTH = 100;
    private final IDepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartment() {
        return departmentRepository.findAll()
                .stream().sorted(Comparator.comparing(Department::getDepartmentId)).toList();
    }

    @Override
    public Department getDepartmentById(Integer id) {
        return departmentRepository.findById(id);
    }

    @Override
    public Set<Integer> findExistingDepartmentIds(List<Integer> departmentIds) {
        return departmentRepository.findExistingIds(departmentIds);
    }

    @Override
    public int getRequiredFieldCount() {
        return 1;
    }

    @Override
    public String getErrorLogFileName() {
        return "insert_department_error_log.csv";
    }

    @Override
    public Department parseFields(List<String> fields) {
        return new Department(fields.get(0).trim());
    }

    @Override
    public String validateFields(Department item) {
        if (!ValidationUtils.isValidLength(item.getDepartmentName(), MIN_LENGTH, MAX_LENGTH)) {
            return String.format("Department name length must from %d to %d", MIN_LENGTH, MAX_LENGTH);
        }
        return null;
    }

    @Override
    public Map<String, Set<String>> loadContext(List<Department> items) {
        List<Department> all = departmentRepository.findAll();
        Map<String, Set<String>> context = new HashMap<>();
        context.put("departmentName", all.stream()
                .map(Department::getDepartmentName)
                .collect(Collectors.toSet()));
        return context;
    }

    @Override
    public Map<String, String> extractKeys(Department item) {
        Map<String, String> keys = new HashMap<>();
        keys.put("departmentName", item.getDepartmentName());
        return keys;
    }

    @Override
    public String validateUniqueKeys(Department item, Map<String, Set<String>> context,
                                     Map<String, Set<String>> seenKeys) {
        String name = item.getDepartmentName();
        List<String> errors = new ArrayList<>();

        if (context.get("departmentName").contains(name)) {
            errors.add("Department name already exist");
        } else if (seenKeys.get("departmentName").contains(name)) {
            errors.add("Department name duplicated in CSV file");
        }

        return errors.isEmpty() ? null : String.join(",", errors);
    }

    @Override
    public int saveAll(List<Department> items) {
        return departmentRepository.saveAll(items);
    }
}
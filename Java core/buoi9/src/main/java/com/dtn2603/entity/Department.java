package com.dtn2603.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private Integer departmentId;
    private String departmentName;

    public Department(Integer departmentId){
        this.departmentId = departmentId;
    }
}

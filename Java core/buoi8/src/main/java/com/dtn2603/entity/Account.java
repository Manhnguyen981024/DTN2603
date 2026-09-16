package com.dtn2603.entity;

import java.time.LocalDate;

import com.dtn2603.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private int accountId;
    private String email;
    private String username;
    private String fullName;
    private Department department;
    private Position position;
    private LocalDate createDate;
    private Gender gender;
}

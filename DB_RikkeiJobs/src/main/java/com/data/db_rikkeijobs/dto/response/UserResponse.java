package com.data.db_rikkeijobs.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String fullName;
    private String status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;
    private String address;
    private String phone;
    private String role;
    private Boolean lock;
    private String permissionList;
    private String gender;
    private String level;
    private String skills;
    private Integer yearsOfExperience;
    private String avatar;
    private String position;
    private LocalDate birthdate;
    private List<String> foreignLanguage;
    private List<Long> saveJob;
}


package com.data.db_rikkeijobs.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "First name is required")
    @Size(max = 255, message = "First name must not exceed 255 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 255, message = "Last name must not exceed 255 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @Size(max = 255, message = "Username must not exceed 255 characters")
    private String userName;

    @Size(max = 255, message = "Full name must not exceed 255 characters")
    private String fullName;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String status;
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


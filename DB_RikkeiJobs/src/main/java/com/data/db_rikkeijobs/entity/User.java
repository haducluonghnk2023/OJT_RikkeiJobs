package com.data.db_rikkeijobs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(name = "user_name")
    private String userName;
    
    @Column(name = "full_name")
    private String fullName;
    
    /** BCrypt hash - lưu vào cả password và password_hash vì DB có cả 2 cột NOT NULL */
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "create_at")
    private LocalDateTime createAt;
    
    @Column(name = "update_at")
    private LocalDateTime updateAt;
    
    @Column(name = "delete_at")
    private LocalDateTime deleteAt;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "role")
    private String role;
    
    @Column(name = "is_locked")
    private Boolean lock;
    
    @Column(name = "permission_list")
    private String permissionList;
    
    @Column(name = "gender")
    private String gender;
    
    @Column(name = "level")
    private String level;
    
    @Column(name = "skills", columnDefinition = "TEXT")
    private String skills;
    
    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;
    
    @Column(name = "avatar", columnDefinition = "TEXT")
    private String avatar;
    
    @Column(name = "position")
    private String position;
    
    @Column(name = "birthdate")
    private LocalDate birthdate;

    /** Đồng bộ password từ password_hash khi đọc user cũ chỉ có password_hash */
    @PostLoad
    void syncPasswordFromHash() {
        if (password == null && passwordHash != null) {
            password = passwordHash;
        }
    }
    
    // Quan hệ One-to-Many với UserForeignLanguage
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserForeignLanguage> foreignLanguages = new ArrayList<>();
    
    // Quan hệ One-to-Many với UserSaveJob
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSaveJob> saveJobs = new ArrayList<>();
}


package com.data.db_rikkeijobs.mapper;

import com.data.db_rikkeijobs.dto.request.CreateUserRequest;
import com.data.db_rikkeijobs.dto.request.UpdateUserRequest;
import com.data.db_rikkeijobs.dto.response.UserResponse;
import com.data.db_rikkeijobs.entity.User;
import com.data.db_rikkeijobs.entity.UserForeignLanguage;
import com.data.db_rikkeijobs.entity.UserSaveJob;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .userName(user.getUserName())
                .fullName(user.getFullName())
                .status(user.getStatus())
                .createAt(user.getCreateAt())
                .updateAt(user.getUpdateAt())
                .deleteAt(user.getDeleteAt())
                .address(user.getAddress())
                .phone(user.getPhone())
                .role(user.getRole())
                .lock(user.getLock())
                .permissionList(user.getPermissionList())
                .gender(user.getGender())
                .level(user.getLevel())
                .skills(user.getSkills())
                .yearsOfExperience(user.getYearsOfExperience())
                .avatar(user.getAvatar())
                .position(user.getPosition())
                .birthdate(user.getBirthdate())
                .foreignLanguage(convertForeignLanguagesToStringList(user.getForeignLanguages()))
                .saveJob(convertSaveJobsToLongList(user.getSaveJobs()))
                .build();
    }

    public User toEntity(CreateUserRequest request) {
        if (request == null) {
            return null;
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setUserName(request.getUserName());
        user.setFullName(request.getFullName());
        user.setPassword(request.getPassword()); // Should be encoded in service
        user.setStatus(request.getStatus());
        user.setCreateAt(LocalDateTime.now());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        user.setLock(request.getLock());
        user.setPermissionList(request.getPermissionList());
        user.setGender(request.getGender());
        user.setLevel(request.getLevel());
        user.setSkills(request.getSkills());
        user.setYearsOfExperience(request.getYearsOfExperience());
        user.setAvatar(request.getAvatar());
        user.setPosition(request.getPosition());
        user.setBirthdate(request.getBirthdate());
        // Convert List<String> to List<UserForeignLanguage>
        if (request.getForeignLanguage() != null && !request.getForeignLanguage().isEmpty()) {
            List<UserForeignLanguage> foreignLanguages = request.getForeignLanguage().stream()
                    .map(language -> {
                        UserForeignLanguage userForeignLanguage = new UserForeignLanguage();
                        userForeignLanguage.setLanguage(language);
                        userForeignLanguage.setUser(user);
                        return userForeignLanguage;
                    })
                    .collect(Collectors.toList());
            user.setForeignLanguages(foreignLanguages);
        }
        // Convert List<Long> to List<UserSaveJob>
        if (request.getSaveJob() != null && !request.getSaveJob().isEmpty()) {
            List<UserSaveJob> saveJobs = request.getSaveJob().stream()
                    .map(jobId -> {
                        UserSaveJob userSaveJob = new UserSaveJob();
                        userSaveJob.setJobId(jobId);
                        userSaveJob.setUser(user);
                        return userSaveJob;
                    })
                    .collect(Collectors.toList());
            user.setSaveJobs(saveJobs);
        }
        return user;
    }

    public void updateEntityFromRequest(UpdateUserRequest request, User user) {
        if (request == null || user == null) {
            return;
        }
        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getUserName() != null) user.setUserName(request.getUserName());
        if (request.getFullName() != null) user.setFullName(request.getFullName());
        if (request.getPassword() != null) user.setPassword(request.getPassword()); // Should be encoded
        if (request.getStatus() != null) user.setStatus(request.getStatus());
        if (request.getAddress() != null) user.setAddress(request.getAddress());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getRole() != null) user.setRole(request.getRole());
        if (request.getLock() != null) user.setLock(request.getLock());
        if (request.getPermissionList() != null) user.setPermissionList(request.getPermissionList());
        if (request.getGender() != null) user.setGender(request.getGender());
        if (request.getLevel() != null) user.setLevel(request.getLevel());
        if (request.getSkills() != null) user.setSkills(request.getSkills());
        if (request.getYearsOfExperience() != null) user.setYearsOfExperience(request.getYearsOfExperience());
        if (request.getAvatar() != null) user.setAvatar(request.getAvatar());
        if (request.getPosition() != null) user.setPosition(request.getPosition());
        if (request.getBirthdate() != null) user.setBirthdate(request.getBirthdate());
        if (request.getForeignLanguage() != null) {
            // Clear existing and set new foreign languages
            user.getForeignLanguages().clear();
            if (!request.getForeignLanguage().isEmpty()) {
                List<UserForeignLanguage> foreignLanguages = request.getForeignLanguage().stream()
                        .map(language -> {
                            UserForeignLanguage userForeignLanguage = new UserForeignLanguage();
                            userForeignLanguage.setLanguage(language);
                            userForeignLanguage.setUser(user);
                            return userForeignLanguage;
                        })
                        .collect(Collectors.toList());
                user.getForeignLanguages().addAll(foreignLanguages);
            }
        }
        if (request.getSaveJob() != null) {
            // Clear existing and set new save jobs
            user.getSaveJobs().clear();
            if (!request.getSaveJob().isEmpty()) {
                List<UserSaveJob> saveJobs = request.getSaveJob().stream()
                        .map(jobId -> {
                            UserSaveJob userSaveJob = new UserSaveJob();
                            userSaveJob.setJobId(jobId);
                            userSaveJob.setUser(user);
                            return userSaveJob;
                        })
                        .collect(Collectors.toList());
                user.getSaveJobs().addAll(saveJobs);
            }
        }
        user.setUpdateAt(LocalDateTime.now());
    }
    
    // Helper methods to convert between List<String>/List<Long> and List<Entity>
    private List<String> convertForeignLanguagesToStringList(List<UserForeignLanguage> foreignLanguages) {
        if (foreignLanguages == null || foreignLanguages.isEmpty()) {
            return new ArrayList<>();
        }
        return foreignLanguages.stream()
                .map(UserForeignLanguage::getLanguage)
                .collect(Collectors.toList());
    }
    
    private List<Long> convertSaveJobsToLongList(List<UserSaveJob> saveJobs) {
        if (saveJobs == null || saveJobs.isEmpty()) {
            return new ArrayList<>();
        }
        return saveJobs.stream()
                .map(UserSaveJob::getJobId)
                .collect(Collectors.toList());
    }
}


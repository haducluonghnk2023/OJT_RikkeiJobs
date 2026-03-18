package com.data.db_rikkeijobs.service;

import com.data.db_rikkeijobs.dto.request.CreateUserRequest;
import com.data.db_rikkeijobs.dto.request.UpdateUserRequest;
import com.data.db_rikkeijobs.dto.response.UserListResult;
import com.data.db_rikkeijobs.dto.response.UserResponse;
import com.data.db_rikkeijobs.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String userName);
    User save(User user);
    User update(Long id, User user);
    void deleteById(Long id);
    boolean existsByEmail(String email);

    UserListResult getAllUserResponses(Integer page, Integer limit, String status, String role, String sort, String order);
    UserResponse getUserResponseById(Long id);
    UserResponse getUserResponseByEmail(String email);
    UserResponse getUserResponseByUserName(String userName);
    UserResponse createUser(CreateUserRequest request);
    UserResponse updateUser(Long id, UpdateUserRequest request);
    UserResponse patchUser(Long id, Map<String, Object> updates);
    void deleteUserOrThrow(Long id);
}


package com.data.db_rikkeijobs.service;

import com.data.db_rikkeijobs.entity.User;

import java.util.List;
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
}


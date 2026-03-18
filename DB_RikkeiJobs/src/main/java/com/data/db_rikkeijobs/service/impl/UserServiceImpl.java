package com.data.db_rikkeijobs.service.impl;

import com.data.db_rikkeijobs.dto.request.CreateUserRequest;
import com.data.db_rikkeijobs.dto.request.UpdateUserRequest;
import com.data.db_rikkeijobs.dto.response.UserListResult;
import com.data.db_rikkeijobs.dto.response.UserResponse;
import com.data.db_rikkeijobs.entity.User;
import com.data.db_rikkeijobs.exception.HttpNotFound;
import com.data.db_rikkeijobs.mapper.UserMapper;
import com.data.db_rikkeijobs.repository.UserRepository;
import com.data.db_rikkeijobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Override
    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
    
    @Override
    public User save(User user) {
        if (user.getPassword() != null) {
            String password = user.getPassword();
            boolean isEncoded = password.startsWith("$2") && password.length() >= 59 && password.length() <= 60;
            String encoded = isEncoded ? password : passwordEncoder.encode(password);
            user.setPassword(encoded);
            user.setPasswordHash(encoded); // DB có cả 2 cột NOT NULL
        }
        return userRepository.save(user);
    }
    
    @Override
    public User update(Long id, User user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setUserName(user.getUserName());
            userToUpdate.setFullName(user.getFullName());
            String pwd = user.getPassword();
            if (pwd != null) {
                boolean isEncoded = pwd.startsWith("$2") && pwd.length() >= 59 && pwd.length() <= 60;
                String encoded = isEncoded ? pwd : passwordEncoder.encode(pwd);
                userToUpdate.setPassword(encoded);
                userToUpdate.setPasswordHash(encoded);
            }
            userToUpdate.setStatus(user.getStatus());
            userToUpdate.setAddress(user.getAddress());
            userToUpdate.setPhone(user.getPhone());
            userToUpdate.setRole(user.getRole());
            userToUpdate.setLock(user.getLock());
            userToUpdate.setPermissionList(user.getPermissionList());
            userToUpdate.setGender(user.getGender());
            userToUpdate.setLevel(user.getLevel());
            userToUpdate.setSkills(user.getSkills());
            userToUpdate.setYearsOfExperience(user.getYearsOfExperience());
            userToUpdate.setAvatar(user.getAvatar());
            userToUpdate.setPosition(user.getPosition());
            userToUpdate.setBirthdate(user.getBirthdate());
            userToUpdate.setForeignLanguages(user.getForeignLanguages());
            userToUpdate.setSaveJobs(user.getSaveJobs());
            userToUpdate.setUpdateAt(java.time.LocalDateTime.now());
            return userRepository.save(userToUpdate);
        }
        throw new RuntimeException("User not found with id: " + id);
    }
    
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserListResult getAllUserResponses(Integer page, Integer limit, String status, String role, String sort, String order) {
        List<User> sourceUsers = userRepository.findAll();

        if (status != null && !status.isBlank()) {
            final String s = status.trim();
            sourceUsers = sourceUsers.stream()
                    .filter(u -> u.getStatus() != null && u.getStatus().equalsIgnoreCase(s))
                    .collect(Collectors.toList());
        }

        if (role != null && !role.isBlank()) {
            final String r = role.trim();
            sourceUsers = sourceUsers.stream()
                    .filter(u -> u.getRole() != null && u.getRole().equalsIgnoreCase(r))
                    .collect(Collectors.toList());
        }

        if (sort != null && sort.equalsIgnoreCase("id")) {
            boolean desc = order != null && order.equalsIgnoreCase("DESC");
            sourceUsers = sourceUsers.stream()
                    .sorted((a, b) -> {
                        Long aId = a.getId();
                        Long bId = b.getId();
                        if (aId == null && bId == null) return 0;
                        if (aId == null) return 1;
                        if (bId == null) return -1;
                        return desc ? bId.compareTo(aId) : aId.compareTo(bId);
                    })
                    .collect(Collectors.toList());
        }

        int totalCount = sourceUsers.size();
        List<UserResponse> users;

        if (page != null && limit != null) {
            int start = (page - 1) * limit;
            users = sourceUsers.stream()
                    .skip(Math.max(0, start))
                    .limit(limit)
                    .map(userMapper::toResponse)
                    .collect(Collectors.toList());
        } else {
            users = sourceUsers.stream()
                    .map(userMapper::toResponse)
                    .collect(Collectors.toList());
        }

        return new UserListResult(users, totalCount);
    }

    @Override
    public UserResponse getUserResponseById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new HttpNotFound("User not found with id: " + id));
    }

    @Override
    public UserResponse getUserResponseByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new HttpNotFound("User not found with email: " + email));
    }

    @Override
    public UserResponse getUserResponseByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new HttpNotFound("User not found with username: " + userName));
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        User user = userMapper.toEntity(request);
        return userMapper.toResponse(save(user));
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new HttpNotFound("User not found with id: " + id));
        userMapper.updateEntityFromRequest(request, existingUser);
        return userMapper.toResponse(update(id, existingUser));
    }

    @Override
    public UserResponse patchUser(Long id, Map<String, Object> updates) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new HttpNotFound("User not found with id: " + id));

        if (updates.containsKey("role")) {
            existingUser.setRole((String) updates.get("role"));
        }
        if (updates.containsKey("status")) {
            existingUser.setStatus((String) updates.get("status"));
        }
        if (updates.containsKey("lock")) {
            Object lockValue = updates.get("lock");
            if (lockValue instanceof Boolean) {
                existingUser.setLock((Boolean) lockValue);
            } else if (lockValue instanceof String) {
                existingUser.setLock(Boolean.parseBoolean((String) lockValue));
            }
        }

        return userMapper.toResponse(update(id, existingUser));
    }

    @Override
    public void deleteUserOrThrow(Long id) {
        if (!userRepository.existsById(id)) {
            throw new HttpNotFound("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}


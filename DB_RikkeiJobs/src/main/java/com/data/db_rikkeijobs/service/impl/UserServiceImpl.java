package com.data.db_rikkeijobs.service.impl;

import com.data.db_rikkeijobs.entity.User;
import com.data.db_rikkeijobs.repository.UserRepository;
import com.data.db_rikkeijobs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
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
            if (!isEncoded) {
                user.setPassword(passwordEncoder.encode(password));
            }
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
            userToUpdate.setPassword(user.getPassword());
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
}


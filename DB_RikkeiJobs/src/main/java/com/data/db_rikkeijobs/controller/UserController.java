package com.data.db_rikkeijobs.controller;

import com.data.db_rikkeijobs.dto.request.CreateUserRequest;
import com.data.db_rikkeijobs.dto.request.UpdateUserRequest;
import com.data.db_rikkeijobs.dto.response.ResponseWrapper;
import com.data.db_rikkeijobs.dto.response.UserResponse;
import com.data.db_rikkeijobs.entity.User;
import com.data.db_rikkeijobs.exception.HttpNotFound;
import com.data.db_rikkeijobs.mapper.UserMapper;
import com.data.db_rikkeijobs.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<?> getAllUsers(
            @RequestParam(required = false) Integer _page,
            @RequestParam(required = false) Integer _limit,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String _sort,
            @RequestParam(required = false) String _order) {
        List<User> sourceUsers = userService.findAll();

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

        if (_sort != null && _sort.equalsIgnoreCase("id")) {
            boolean desc = _order != null && _order.equalsIgnoreCase("DESC");
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

        List<UserResponse> users;
        
        if (_page != null && _limit != null) {
            int totalUsers = sourceUsers.size();
            int start = (_page - 1) * _limit;
            int end = Math.min(start + _limit, totalUsers);
            
            users = sourceUsers.stream()
                    .skip(Math.max(0, start))
                    .limit(_limit)
                    .map(userMapper::toResponse)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok()
                    .header("x-total-count", String.valueOf(totalUsers))
                    .body(ResponseWrapper.builder()
                            .status(HttpStatus.OK)
                            .code(HttpStatus.OK.value())
                            .data(users)
                            .message("Users retrieved successfully")
                            .build());
        } else {
            users = sourceUsers.stream()
                    .map(userMapper::toResponse)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(
                    ResponseWrapper.builder()
                            .status(HttpStatus.OK)
                            .code(HttpStatus.OK.value())
                            .data(users)
                            .message("Users retrieved successfully")
                            .build()
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserResponse user = userService.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new HttpNotFound("User not found with id: " + id));
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(user)
                        .message("User retrieved successfully")
                        .build()
        );
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        UserResponse user = userService.findByEmail(email)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new HttpNotFound("User not found with email: " + email));
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(user)
                        .message("User retrieved successfully")
                        .build()
        );
    }

    @GetMapping("/username/{userName}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String userName) {
        UserResponse user = userService.findByUserName(userName)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new HttpNotFound("User not found with username: " + userName));
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(user)
                        .message("User retrieved successfully")
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = userMapper.toEntity(request);
        UserResponse createdUser = userMapper.toResponse(userService.save(user));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(HttpStatus.CREATED.value())
                        .data(createdUser)
                        .message("User created successfully")
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, 
                                       @Valid @RequestBody UpdateUserRequest request) {
        User existingUser = userService.findById(id)
                .orElseThrow(() -> new HttpNotFound("User not found with id: " + id));
        
        userMapper.updateEntityFromRequest(request, existingUser);
        UserResponse updatedUser = userMapper.toResponse(userService.update(id, existingUser));
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updatedUser)
                        .message("User updated successfully")
                        .build()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchUser(@PathVariable Long id, @RequestBody java.util.Map<String, Object> updates) {
        User existingUser = userService.findById(id)
                .orElseThrow(() -> new HttpNotFound("User not found with id: " + id));
        
        // Update only provided fields
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
        
        UserResponse updatedUser = userMapper.toResponse(userService.update(id, existingUser));
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updatedUser)
                        .message("User updated successfully")
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userService.findById(id).isPresent()) {
            throw new HttpNotFound("User not found with id: " + id);
        }
        
        userService.deleteById(id);
        
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data("User deleted successfully")
                        .message("User deleted successfully")
                        .build()
        );
    }
}


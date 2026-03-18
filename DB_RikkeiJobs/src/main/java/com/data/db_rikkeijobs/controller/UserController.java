package com.data.db_rikkeijobs.controller;

import com.data.db_rikkeijobs.dto.request.CreateUserRequest;
import com.data.db_rikkeijobs.dto.request.UpdateUserRequest;
import com.data.db_rikkeijobs.dto.response.ResponseWrapper;
import com.data.db_rikkeijobs.dto.response.UserResponse;
import com.data.db_rikkeijobs.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(
            @RequestParam(required = false) Integer _page,
            @RequestParam(required = false) Integer _limit,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String _sort,
            @RequestParam(required = false) String _order) {
        var result = userService.getAllUserResponses(_page, _limit, status, role, _sort, _order);
        if (_page != null && _limit != null) {
            return ResponseEntity.ok()
                    .header("x-total-count", String.valueOf(result.getTotalCount()))
                    .body(ResponseWrapper.builder()
                            .status(HttpStatus.OK)
                            .code(HttpStatus.OK.value())
                            .data(result.getUsers())
                            .message("Users retrieved successfully")
                            .build());
        }
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(result.getUsers())
                        .message("Users retrieved successfully")
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserResponseById(id);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(user)
                        .message("User retrieved successfully")
                        .build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        UserResponse user = userService.getUserResponseByEmail(email);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(user)
                        .message("User retrieved successfully")
                        .build());
    }

    @GetMapping("/username/{userName}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String userName) {
        UserResponse user = userService.getUserResponseByUserName(userName);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(user)
                        .message("User retrieved successfully")
                        .build());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse createdUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(HttpStatus.CREATED.value())
                        .data(createdUser)
                        .message("User created successfully")
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                       @Valid @RequestBody UpdateUserRequest request) {
        UserResponse updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updatedUser)
                        .message("User updated successfully")
                        .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchUser(@PathVariable Long id, @RequestBody java.util.Map<String, Object> updates) {
        UserResponse updatedUser = userService.patchUser(id, updates);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data(updatedUser)
                        .message("User updated successfully")
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUserOrThrow(id);
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data("User deleted successfully")
                        .message("User deleted successfully")
                        .build());
    }
}

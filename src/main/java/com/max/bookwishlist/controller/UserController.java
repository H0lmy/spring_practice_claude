package com.max.bookwishlist.controller;

import com.max.bookwishlist.dto.CreateUserRequest;
import com.max.bookwishlist.dto.UpdateUserRequest;
import com.max.bookwishlist.dto.UserResponse;
import com.max.bookwishlist.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
        public UserResponse createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
            return  UserResponse.from(userService.createUser(createUserRequest));
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers().stream().map(UserResponse::from).toList();
    }

    @GetMapping("/users/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return UserResponse.from(userService.getUserById(id));
    }

    @PutMapping("/users/{id}")
    public UserResponse updateUser(  @PathVariable Long id, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return UserResponse.from( userService.updateUser(id, updateUserRequest));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }



}

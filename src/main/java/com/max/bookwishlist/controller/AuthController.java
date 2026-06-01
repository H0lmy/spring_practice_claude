package com.max.bookwishlist.controller;

import com.max.bookwishlist.dto.*;
import com.max.bookwishlist.model.User;
import com.max.bookwishlist.security.UserPrincipal;
import com.max.bookwishlist.service.AuthService;
import com.max.bookwishlist.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
    @PostMapping("/refresh")
    public LoginResponse refresh(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refresh(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest request) {
        authService.logout(request);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/me")
    UserResponse getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return UserResponse.from(userService.getUserById(userPrincipal.getId()));
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,@AuthenticationPrincipal UserPrincipal userPrincipal) {
        userService.changePassword(userPrincipal.getId(),changePasswordRequest);
        return ResponseEntity.noContent().build();
    }
}

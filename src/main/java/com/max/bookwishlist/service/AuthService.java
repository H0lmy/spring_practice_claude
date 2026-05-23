package com.max.bookwishlist.service;

import com.max.bookwishlist.dto.LoginRequest;
import com.max.bookwishlist.dto.LoginResponse;
import com.max.bookwishlist.dto.RefreshTokenRequest;
import com.max.bookwishlist.model.User;
import com.max.bookwishlist.security.CustomUserDetailsService;
import com.max.bookwishlist.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public LoginResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        UserPrincipal principal = (UserPrincipal) customUserDetailsService.loadUserByUsername(loginRequest.getEmail());
        String accessToken = jwtService.generateJwtToken(principal);
        String refreshToken = refreshTokenService.createRefreshToken(principal.getUser());
        return new LoginResponse(accessToken, refreshToken);
    }

    public LoginResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        User user = refreshTokenService.validateAndGetUser(refreshTokenRequest.getRefreshToken());

        refreshTokenService.revoke(refreshTokenRequest.getRefreshToken());

        UserPrincipal principal = new UserPrincipal(user);
        String newAccessToken = jwtService.generateJwtToken(principal);
        String newRefreshToken = refreshTokenService.createRefreshToken(user);

        return new LoginResponse(newAccessToken, newRefreshToken);

    }

    public void logout(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.revoke(refreshTokenRequest.getRefreshToken());
    }
}

package com.max.bookwishlist.service;

import com.max.bookwishlist.model.RefreshToken;
import com.max.bookwishlist.model.User;
import com.max.bookwishlist.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.time.Instant;
import java.util.UUID;



@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-expiration-ms}")
    private Long refreshExpirationMs;

    public String createRefreshToken(User user) {
        String rawToken = UUID.randomUUID().toString();
        String hashed = hash(rawToken);

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setTokenHash(hashed);
        refreshToken.setUser(user);
        refreshToken.setExpiresAt(Instant.now().plusMillis(refreshExpirationMs));
        refreshToken.setRevoked(false);

        refreshTokenRepository.save(refreshToken);
        return rawToken;





    }

    public User validateAndGetUser(String rawToken) {
        String hashed = hash(rawToken);
        RefreshToken refreshToken = refreshTokenRepository.findByTokenHash(hashed).orElseThrow(()->
                new AccessDeniedException("Invalid refresh token"));
        if (refreshToken.isRevoked()) {
            throw new AccessDeniedException("Refresh token revoked");
        }
        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
            throw new AccessDeniedException("Refresh token expired");
        }

        return refreshToken.getUser();
    }

    public void revoke(String rawToken) {
        String hashed = hash(rawToken);
        refreshTokenRepository.findByTokenHash(hashed).ifPresent(refreshToken -> {
            refreshToken.setRevoked(true);
            refreshTokenRepository.save(refreshToken);
        });
    }

    private String hash(String input) {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hashBytes);

        }catch (NoSuchAlgorithmException e){
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}

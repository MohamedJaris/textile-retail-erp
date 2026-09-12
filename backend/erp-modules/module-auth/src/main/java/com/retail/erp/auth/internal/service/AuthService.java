package com.retail.erp.auth.internal.service;

import com.retail.erp.auth.api.dto.LoginRequest;
import com.retail.erp.auth.api.dto.LoginResponse;
import com.retail.erp.auth.api.dto.RefreshTokenRequest;
import com.retail.erp.auth.internal.entity.Role;
import com.retail.erp.auth.internal.entity.User;
import com.retail.erp.auth.internal.repository.UserRepository;
import com.retail.erp.auth.internal.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Value("${app.jwt.access-token-expiry-ms}")
    private long accessTokenExpiryMs;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = userRepository.findByUsername(request.username())
            .orElseThrow(() -> new RuntimeException("User not found"));

        user.setLastLoginAt(Instant.now());
        userRepository.save(user);

        return buildLoginResponse(user);
    }

    @Transactional
    public LoginResponse refreshToken(RefreshTokenRequest request) {
        String token = request.refreshToken();
        if (!jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String username = jwtTokenProvider.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isActive()) {
            throw new RuntimeException("User is inactive");
        }

        return buildLoginResponse(user);
    }

    private LoginResponse buildLoginResponse(User user) {
        List<String> roleNames = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        List<String> permissions = user.getAllPermissionCodes().stream().toList();

        String accessToken = jwtTokenProvider.generateAccessToken(user.getUsername(), roleNames, permissions);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUsername());

        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
            user.getId(),
            user.getUsername(),
            user.getFullName(),
            roleNames,
            permissions
        );

        return new LoginResponse(accessToken, refreshToken, accessTokenExpiryMs, userInfo);
    }
}

package com.retail.erp.auth.internal.controller;

import com.retail.erp.auth.api.dto.LoginRequest;
import com.retail.erp.auth.api.dto.LoginResponse;
import com.retail.erp.auth.api.dto.RefreshTokenRequest;
import com.retail.erp.auth.api.dto.UserProfileResponse;
import com.retail.erp.auth.internal.service.AuthService;
import com.retail.erp.auth.internal.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public LoginResponse refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }

    @GetMapping("/me")
    public UserProfileResponse getMe(Authentication authentication) {
        return userService.getCurrentUser(authentication.getName());
    }
}

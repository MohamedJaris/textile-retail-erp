package com.retail.erp.auth.api.dto;

import java.util.List;

public record LoginResponse(
    String accessToken,
    String refreshToken,
    String tokenType,
    long expiresIn,
    UserInfo userInfo
) {
    public LoginResponse(String accessToken, String refreshToken, long expiresIn, UserInfo userInfo) {
        this(accessToken, refreshToken, "Bearer", expiresIn, userInfo);
    }

    public record UserInfo(
        Long id,
        String username,
        String fullName,
        List<String> roles,
        List<String> permissions
    ) {}
}

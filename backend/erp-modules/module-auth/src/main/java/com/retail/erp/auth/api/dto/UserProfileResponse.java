package com.retail.erp.auth.api.dto;

import java.time.Instant;
import java.util.List;

public record UserProfileResponse(
    Long id,
    String username,
    String fullName,
    String email,
    String phone,
    boolean active,
    List<String> roles,
    List<String> permissions,
    Instant lastLoginAt,
    Instant createdAt
) {}

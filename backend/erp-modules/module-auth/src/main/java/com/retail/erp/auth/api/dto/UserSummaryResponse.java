package com.retail.erp.auth.api.dto;

import java.time.Instant;
import java.util.List;

public record UserSummaryResponse(
    Long id,
    String username,
    String fullName,
    String email,
    boolean active,
    List<String> roles,
    Instant createdAt
) {}

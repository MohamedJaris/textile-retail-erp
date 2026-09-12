package com.retail.erp.auth.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
    @NotBlank String username,
    @NotBlank @Size(min = 4) String password
) {}

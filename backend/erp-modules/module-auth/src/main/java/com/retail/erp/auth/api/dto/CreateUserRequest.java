package com.retail.erp.auth.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record CreateUserRequest(
    @NotBlank @Size(min = 3, max = 50) String username,
    @NotBlank @Size(min = 6) String password,
    @NotBlank String fullName,
    @Email String email,
    String phone,
    @NotEmpty Set<String> roleNames
) {}

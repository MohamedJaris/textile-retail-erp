package com.retail.erp.auth.api.dto;

import jakarta.validation.constraints.Email;
import java.util.Set;

public record UpdateUserRequest(
    String fullName,
    @Email String email,
    String phone,
    Set<String> roleNames,
    Boolean active
) {}

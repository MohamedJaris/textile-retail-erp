package com.retail.erp.auth.internal.controller;

import com.retail.erp.auth.api.dto.ChangePasswordRequest;
import com.retail.erp.auth.api.dto.CreateUserRequest;
import com.retail.erp.auth.api.dto.UpdateUserRequest;
import com.retail.erp.auth.api.dto.UserProfileResponse;
import com.retail.erp.auth.api.dto.UserSummaryResponse;
import com.retail.erp.auth.internal.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public UserSummaryResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER_VIEW')")
    public Page<UserSummaryResponse> listUsers(Pageable pageable) {
        return userService.listUsers(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_VIEW')")
    public UserProfileResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public UserSummaryResponse updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        return userService.updateUser(id, request);
    }

    @PostMapping("/{id}/change-password")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('USER_UPDATE') or @userSecurity.isCurrentUser(authentication, #id)")
    public void changePassword(
        @PathVariable Long id,
        @Valid @RequestBody ChangePasswordRequest request,
        Authentication authentication
    ) {
        // Implementation logic expects username or current user handling in service.
        // Assuming user uses their own username to change password if they are changing their own.
        // If an admin resets, they might skip current password check, but keeping simple here.
        userService.changePassword(authentication.getName(), request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public void deleteUser(@PathVariable Long id) {
        userService.deactivateUser(id);
    }
}

package com.retail.erp.auth.internal.service;

import com.retail.erp.auth.api.dto.ChangePasswordRequest;
import com.retail.erp.auth.api.dto.CreateUserRequest;
import com.retail.erp.auth.api.dto.UpdateUserRequest;
import com.retail.erp.auth.api.dto.UserProfileResponse;
import com.retail.erp.auth.api.dto.UserSummaryResponse;
import com.retail.erp.auth.internal.entity.Role;
import com.retail.erp.auth.internal.entity.User;
import com.retail.erp.auth.internal.repository.RoleRepository;
import com.retail.erp.auth.internal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserProfileResponse getCurrentUser(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToProfile(user);
    }

    public UserProfileResponse getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToProfile(user);
    }

    public UserSummaryResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username already exists");
        }
        if (request.email() != null && userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPhone(request.phone());

        List<Role> roles = roleRepository.findByNameIn(request.roleNames());
        user.setRoles(roles.stream().collect(Collectors.toSet()));

        User savedUser = userRepository.save(user);
        return mapToSummary(savedUser);
    }

    public UserSummaryResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.fullName() != null) user.setFullName(request.fullName());
        if (request.email() != null) user.setEmail(request.email());
        if (request.phone() != null) user.setPhone(request.phone());
        if (request.active() != null) user.setActive(request.active());
        if (request.roleNames() != null) {
            List<Role> roles = roleRepository.findByNameIn(request.roleNames());
            user.setRoles(roles.stream().collect(Collectors.toSet()));
        }

        User updatedUser = userRepository.save(user);
        return mapToSummary(updatedUser);
    }

    public void changePassword(String username, ChangePasswordRequest request) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Current password does not match");
        }

        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    public Page<UserSummaryResponse> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::mapToSummary);
    }

    public void deactivateUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(false);
        userRepository.save(user);
    }

    private UserSummaryResponse mapToSummary(User user) {
        return new UserSummaryResponse(
            user.getId(),
            user.getUsername(),
            user.getFullName(),
            user.getEmail(),
            user.isActive(),
            user.getRoles().stream().map(Role::getName).collect(Collectors.toList()),
            user.getCreatedAt()
        );
    }

    private UserProfileResponse mapToProfile(User user) {
        return new UserProfileResponse(
            user.getId(),
            user.getUsername(),
            user.getFullName(),
            user.getEmail(),
            user.getPhone(),
            user.isActive(),
            user.getRoles().stream().map(Role::getName).collect(Collectors.toList()),
            user.getAllPermissionCodes().stream().toList(),
            user.getLastLoginAt(),
            user.getCreatedAt()
        );
    }
}

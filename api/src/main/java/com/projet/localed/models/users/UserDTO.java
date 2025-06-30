package com.projet.localed.models.users;

import com.projet.localed.enums.UserRole;
import com.projet.localed.enums.UserStatus;
import lombok.Builder;

@Builder
public record UserDTO(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        UserRole role,
        UserStatus status
) {}

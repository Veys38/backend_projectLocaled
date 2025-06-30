package com.projet.localed.models.users;

import com.projet.localed.enums.UserRole;
import com.projet.localed.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserForm(
        @NotBlank String username,
        @Email String email,
        @NotBlank String password,
        String firstName,
        String lastName,
        String phoneNumber,
        @NotNull UserRole role,
        @NotNull UserStatus status
) {
}

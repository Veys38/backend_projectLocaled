package com.projet.localed.models.security.forms;

import com.projet.localed.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterForm(
        @NotBlank String username,
        @Email String email,
        @NotBlank String password
) {
    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}

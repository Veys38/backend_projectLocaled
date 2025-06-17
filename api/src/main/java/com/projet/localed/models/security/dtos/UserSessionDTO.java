package com.projet.localed.models.security.dtos;

public record UserSessionDTO(Long id, String username, String email, String role) {
    public static UserSessionDTO fromUser(User user) {
        return new UserSessionDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole().name());
    }
}
package com.projet.localed.models.security.dtos;

public record UserTokenDTO(UserSessionDTO user, String token) {}

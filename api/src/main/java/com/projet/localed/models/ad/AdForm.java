package com.projet.localed.models.ad;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AdForm(
        @NotBlank String title,
        String description,
        BigDecimal price,
        LocalDateTime expirationDate,
        boolean isAvailable,
        boolean isHighlighted,
        LocalDateTime highlightStartDate,
        LocalDateTime highlightEndDate,
        String location,
        @NotNull Long userId,
        Long categoryId
) {}
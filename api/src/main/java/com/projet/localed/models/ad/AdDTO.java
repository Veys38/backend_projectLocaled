package com.projet.localed.models.ad;

import com.projet.localed.enums.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record AdDTO(
        Long id,
        String title,
        String description,
        BigDecimal price,
        LocalDateTime expirationDate,
        boolean isAvailable,
        boolean isHighlighted,
        LocalDateTime highlightStartDate,
        LocalDateTime highlightEndDate,
        String location,
        Long userId,
        Long categoryId
) {}
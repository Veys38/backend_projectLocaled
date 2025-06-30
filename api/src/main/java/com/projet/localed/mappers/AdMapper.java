package com.projet.localed.mappers;

import com.projet.localed.entities.Ad;
import com.projet.localed.entities.Category;
import com.projet.localed.entities.User;
import com.projet.localed.models.ad.AdDTO;
import com.projet.localed.models.ad.AdForm;
import org.springframework.stereotype.Component;

@Component
public class AdMapper {
    public AdDTO toDTO(Ad ad) {
        return AdDTO.builder()
                .id(ad.getId())
                .title(ad.getTitle())
                .description(ad.getDescription())
                .price(ad.getPrice())
                .expirationDate(ad.getExpirationDate())
                .isAvailable(ad.isAvailable())
                .isHighlighted(ad.isHighlighted())
                .highlightStartDate(ad.getHighlightStartDate())
                .highlightEndDate(ad.getHighlightEndDate())
                .location(ad.getLocation() != null ? ad.getLocation().toString() : null)
                .userId(ad.getUser().getId())
                .categoryId(ad.getCategory() != null ? ad.getCategory().getId() : null)
                .build();
    }

    public Ad fromForm(AdForm form, User user, Category category) {
        Ad ad = new Ad();
        ad.setTitle(form.title());
        ad.setDescription(form.description());
        ad.setPrice(form.price());
        ad.setExpirationDate(form.expirationDate());
        ad.setAvailable(form.isAvailable());
        ad.setHighlighted(form.isHighlighted());
        ad.setHighlightStartDate(form.highlightStartDate());
        ad.setHighlightEndDate(form.highlightEndDate());
        // tu peux convertir String -> Address si tu veux
        ad.setUser(user);
        ad.setCategory(category);
        return ad;
    }
}


package com.projet.localed.services.ad.impls;

import com.projet.localed.entities.Ad;
import com.projet.localed.enums.UserRole;
import com.projet.localed.enums.UserStatus;
import com.projet.localed.entities.User;
import com.projet.localed.exceptions.ad.AdNotFoundException;
import com.projet.localed.repositories.AdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdServiceImplTest {

    private AdRepository adRepository;
    private AdServiceImpl adService;

    @BeforeEach
    void setUp() {
        adRepository = mock(AdRepository.class);
        adService = new AdServiceImpl(adRepository);
    }

    @Test
    void getAll_shouldReturnList() {
        when(adRepository.findAll()).thenReturn(List.of(new Ad(), new Ad()));
        assertThat(adService.getAll()).hasSize(2);
        verify(adRepository).findAll();
    }

    @Test
    void getById_shouldReturnAd_whenExists() {
        Ad ad = new Ad();
        ad.setTitle("Test Ad");
        when(adRepository.findById(1L)).thenReturn(Optional.of(ad));
        assertThat(adService.getById(1L).getTitle()).isEqualTo("Test Ad");
    }

    @Test
    void getById_shouldThrow_whenNotFound() {
        when(adRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> adService.getById(99L))
                .isInstanceOf(AdNotFoundException.class)
                .hasMessageContaining("Annonce introuvable");
    }

    @Test
    void create_shouldSaveAndReturnAd() {
        Ad ad = new Ad();
        when(adRepository.save(ad)).thenReturn(ad);
        assertThat(adService.create(ad)).isEqualTo(ad);
    }

    @Test
    void delete_shouldRemoveAd() {
        adService.delete(1L);
        verify(adRepository).deleteById(1L);
    }
}

package com.projet.localed.controllers.ad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.localed.entities.Ad;
import com.projet.localed.entities.User;
import com.projet.localed.enums.UserRole;
import com.projet.localed.enums.UserStatus;
import com.projet.localed.repositories.AdRepository;
import com.projet.localed.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "admin", roles = {"ADMIN"})
class AdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long adId;

    @BeforeEach
    void setUp() {
        adRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("user1");
        user.setEmail("user1@mail.com");
        user.setPassword("pass");
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.ACTIVE);
        user = userRepository.save(user);

        Ad ad = new Ad();
        ad.setTitle("Annonce test");
        ad.setDescription("Une annonce de test");
        ad.setPrice(BigDecimal.valueOf(99.99));
        ad.setExpirationDate(LocalDateTime.now().plusDays(10));
        ad.setUser(user);
        ad = adRepository.save(ad);

        adId = ad.getId();
    }

    @Test
    void getById_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/ads/{id}", adId))
                .andExpect(status().isOk());
    }

    @Test
    void getAll_shouldReturnList() throws Exception {
        mockMvc.perform(get("/ads"))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/ads/{id}", adId))
                .andExpect(status().isNoContent());
        assertThat(adRepository.findById(adId)).isEmpty();
    }
}

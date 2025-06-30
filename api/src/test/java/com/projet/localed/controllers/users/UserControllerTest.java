package com.projet.localed.controllers.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.localed.entities.User;
import com.projet.localed.enums.UserRole;
import com.projet.localed.enums.UserStatus;
import com.projet.localed.models.users.UserForm;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "admin", roles = {"ADMIN"}) // ⬅️ Pour autoriser toutes les requêtes
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long userId;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@mail.com");
        user.setPassword("password");
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.ACTIVE);

        userId = userRepository.save(user).getId();
    }

    @Test
    void getById_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isOk());
    }

    @Test
    void create_shouldReturnCreated() throws Exception {
        UserForm form = new UserForm(
                "newuser",
                "new@mail.com",
                "pass123",
                null, null, null,
                UserRole.USER,
                UserStatus.ACTIVE
        );

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isCreated());

        assertThat(userRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    void update_shouldReturnNoContent() throws Exception {
        UserForm form = new UserForm(
                "updateduser",
                "updated@mail.com",
                "newpass",
                null, null, null,
                UserRole.USER,
                UserStatus.ACTIVE
        );

        mockMvc.perform(put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isNoContent());

        User updated = userRepository.findById(userId).orElseThrow();
        assertThat(updated.getEmail()).isEqualTo("updated@mail.com");
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().isNoContent());

        assertThat(userRepository.findById(userId)).isEmpty();
    }
}

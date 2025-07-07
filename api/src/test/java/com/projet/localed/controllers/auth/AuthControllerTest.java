package com.projet.localed.controllers.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.localed.entities.User;
import com.projet.localed.enums.UserRole;
import com.projet.localed.enums.UserStatus;
import com.projet.localed.models.security.forms.RegisterForm;
import com.projet.localed.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Primary
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void cleanDb() {
        userRepository.deleteAll();
    }

    @Test
    void register_shouldReturnNoContent() throws Exception {
        RegisterForm form = new RegisterForm(
                "user123",
                "user123@mail.com",
                "password123"
        );

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isNoContent());
    }

    @Test
    void login_shouldReturnToken() throws Exception {
        RegisterForm form = new RegisterForm(
                "loginUser",
                "login@mail.com",
                "mypassword"
        );

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isNoContent());

        String loginJson = "{\"email\": \"login@mail.com\", \"password\": \"mypassword\"}";

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk());
    }

    @Test
    void verifyEmail_shouldEnableUser_whenTokenIsValid() throws Exception {
        // Arrange
        User user = new User();
        user.setUsername("verifyUser1");
        user.setEmail("verifyUser1@mail.com");
        user.setPassword("password");
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.PENDING);
        user.setEnabled(false);
        user.setConfirmationToken("token-unique-verify");

        userRepository.save(user);
        entityManager.flush();

        // Act
        mockMvc.perform(get("/auth/verify-email")
                        .param("token", "token-unique-verify"))
                .andExpect(status().isNoContent());

        // Assert (on recharge depuis la DB)
        User verified = userRepository.findByEmail("verifyUser1@mail.com").orElseThrow();
        assertThat(verified.isEnabled()).isTrue();
        assertThat(verified.getConfirmationToken()).isNull();
    }




    @Test
    void verifyEmail_shouldFail_whenAlreadyEnabled() throws Exception {
        User user = new User();
        user.setUsername("alreadyEnabled");
        user.setEmail("already@mail.com");
        user.setPassword("password");
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.ACTIVE);
        user.setEnabled(true);
        user.setConfirmationToken("token-already-999");

        userRepository.save(user);

        mockMvc.perform(get("/auth/verify-email")
                        .param("token", "token-already-999"))
                .andExpect(status().isBadRequest());
    }
}

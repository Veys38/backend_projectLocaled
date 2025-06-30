package com.projet.localed.controllers.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.localed.models.security.forms.RegisterForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Primary
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

        // On enregistre d'abord l'utilisateur
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isNoContent());

        // On tente de se connecter ensuite
        String loginJson = """
            {
                "email": "login@mail.com",
                "password": "mypassword"
            }
            """;

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk());
    }

}

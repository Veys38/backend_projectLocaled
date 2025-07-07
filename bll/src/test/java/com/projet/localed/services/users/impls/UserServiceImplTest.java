package com.projet.localed.services.users.impls;

import com.projet.localed.entities.User;
import com.projet.localed.enums.UserRole;
import com.projet.localed.enums.UserStatus;
import com.projet.localed.exceptions.user.UserNotFoundException;
import com.projet.localed.repositories.UserRepository;
import com.projet.localed.services.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private UserServiceImpl userService;
    private EmailService emailService; // nouveau champ


    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        emailService = mock(EmailService.class);
        userService = new UserServiceImpl(userRepository, emailService);
    }

    @Test
    void getAll_shouldReturnListOfUsers() {
        User u1 = new User();
        u1.setUsername("u1");
        u1.setEmail("u1@mail.com");
        u1.setPassword("pass");
        u1.setRole(UserRole.USER);
        u1.setStatus(UserStatus.ACTIVE);

        User u2 = new User();
        u2.setUsername("u2");
        u2.setEmail("u2@mail.com");
        u2.setPassword("pass");
        u2.setRole(UserRole.USER);
        u2.setStatus(UserStatus.ACTIVE);

        when(userRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<User> result = userService.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getEmail()).isEqualTo("u1@mail.com");
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getById_shouldReturnUser_whenExists() {
        User u1 = new User();
        u1.setUsername("u1");
        u1.setEmail("u1@mail.com");
        u1.setPassword("pass");
        u1.setRole(UserRole.USER);
        u1.setStatus(UserStatus.ACTIVE);

        when(userRepository.findById(1L)).thenReturn(Optional.of(u1));

        User result = userService.getById(1L);

        assertThat(result.getEmail()).isEqualTo("u1@mail.com");
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getById_shouldThrowException_whenNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getById(99L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("Utilisateur introuvable");

        verify(userRepository, times(1)).findById(99L);
    }

    @Test
    void verifyEmail_shouldEnableUser_whenTokenIsValid() {
        // Arrange
        User user = new User();
        user.setUsername("test");
        user.setEmail("test@mail.com");
        user.setPassword("pass");
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.ACTIVE);
        user.setConfirmationToken("abc123");
        user.setEnabled(false);

        when(userRepository.findByConfirmationToken("abc123")).thenReturn(Optional.of(user));

        // Act
        userService.verifyEmail("abc123");

        // Assert
        assertThat(user.isEnabled()).isTrue();
        assertThat(user.getConfirmationToken()).isNull();
        verify(userRepository).save(user);
    }

}

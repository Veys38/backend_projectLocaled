package com.projet.localed.services.security.impls;

import com.projet.localed.entities.User;
import com.projet.localed.enums.UserRole;
import com.projet.localed.exceptions.user.BadCredentialsException;
import com.projet.localed.exceptions.user.UserNotFoundException;
import com.projet.localed.repositories.UserRepository;
import com.projet.localed.services.users.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    private final UserService userService = mock(UserService.class);
    private final AuthServiceImpl authService = new AuthServiceImpl(userRepository, passwordEncoder, userService);

    @Test
    void login_shouldReturnUser_whenCredentialsAreCorrect() {
        // Arrange
        String email = "test@mail.com";
        String rawPassword = "123456";
        String encodedPassword = "encoded";

        User fakeUser = new User();
        fakeUser.setEmail(email);
        fakeUser.setPassword(encodedPassword);
        fakeUser.setRole(UserRole.USER);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(fakeUser));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        // Act
        User result = authService.login(email, rawPassword);

        // Assert
        assertEquals(email, result.getEmail());
        assertEquals(UserRole.USER, result.getRole());
    }

    @Test
    void login_shouldThrowException_whenPasswordIsWrong() {
        // Arrange
        String email = "test@mail.com";
        String rawPassword = "wrong";
        String encodedPassword = "encoded";

        User fakeUser = new User();
        fakeUser.setEmail(email);
        fakeUser.setPassword(encodedPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(fakeUser));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        // Assert
        assertThrows(BadCredentialsException.class, () -> authService.login(email, rawPassword));
    }

    @Test
    void login_shouldThrowException_whenUserNotFound() {
        // Arrange
        String email = "notfound@mail.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Assert
        assertThrows(UserNotFoundException.class, () -> authService.login(email, "any"));
    }

    @Test
    void register_shouldSaveUserWithEncodedPasswordAndUserRole() {
        // Arrange
        User user = new User();
        user.setEmail("test@mail.com");
        user.setPassword("raw");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(passwordEncoder.encode("raw")).thenReturn("encoded");

        // Act
        authService.register(user);

        // Assert
        assertEquals("encoded", user.getPassword());
        assertEquals(UserRole.USER, user.getRole());
        when(userService.create(user)).thenReturn(user);
        verify(userService, times(1)).create(user);
    }

    @Test
    void findByEmail_shouldReturnUser_whenEmailExists() {
        // given
        String email = "test@mail.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // when
        User result = authService.findByEmail(email);

        // then
        assertEquals(user, result);
        verify(userRepository).findByEmail(email);
    }

    @Test
    void findByEmail_shouldThrow_whenEmailNotFound() {
        // given
        String email = "notfound@mail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // then
        assertThrows(UserNotFoundException.class, () -> authService.findByEmail(email));
        verify(userRepository).findByEmail(email);
    }




}

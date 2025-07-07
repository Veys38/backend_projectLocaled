package com.projet.localed.services.security.impls;

import com.projet.localed.entities.User;

import com.projet.localed.enums.UserRole;
import com.projet.localed.enums.UserStatus;
import com.projet.localed.exceptions.user.BadCredentialsException;
import com.projet.localed.exceptions.user.EmailAlreadyVerifiedException;
import com.projet.localed.exceptions.user.UserNotFoundException;
import com.projet.localed.repositories.UserRepository;
import com.projet.localed.services.security.AuthService;
import com.projet.localed.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@Primary
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService; // AJOUTE CETTE LIGNE


    @Override
    public void register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserNotFoundException(HttpStatus.NOT_ACCEPTABLE, "Email déjà utilisé.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.ACTIVE);
        user.setEnabled(false);
        user.setConfirmationToken(UUID.randomUUID().toString());

        userService.create(user); // Appelle le create() dans UserServiceImpl
    }


    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé.")
        );
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException(HttpStatus.NOT_ACCEPTABLE, "Mot de passe incorrect.");
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND, email)
        );
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
    }

    @Override
    public void verifyEmail(String token) {
        User user = userRepository.findByConfirmationToken(token)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "Token invalide"));

        if (user.isEnabled()) {
            throw new EmailAlreadyVerifiedException();
        }

        user.setEnabled(true);
        user.setStatus(UserStatus.ACTIVE); // ← ajoute cette ligne
        user.setConfirmationToken(null);
        userRepository.save(user);
    }





}
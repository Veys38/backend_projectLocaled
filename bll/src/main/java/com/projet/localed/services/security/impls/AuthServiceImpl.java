package com.projet.localed.services.security.impls;

import com.projet.localed.entities.User;

import com.projet.localed.repositories.UserRepository;
import com.projet.localed.services.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(User user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new UserNotFoundException(HttpStatus.NOT_ACCEPTABLE, "Email déjà utilisé.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
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
}
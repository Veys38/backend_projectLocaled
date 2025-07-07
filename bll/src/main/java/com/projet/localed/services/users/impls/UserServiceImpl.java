package com.projet.localed.services.users.impls;

import com.projet.localed.entities.User;
import com.projet.localed.exceptions.user.UserNotFoundException;
import com.projet.localed.repositories.UserRepository;
import com.projet.localed.services.email.EmailService;
import com.projet.localed.services.users.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));
    }

    @Override
    public User create(User user) {
        User saved = userRepository.save(user);

        emailService.send(
                saved.getEmail(),
                "Confirmation de votre compte",
                "Cliquez sur le lien pour confirmer : http://localhost:4200/verify-email?token=" + saved.getConfirmationToken()
        );

        return saved;
    }



    @Override
    public void update(Long id, User user) {
        User existing = getById(id);
        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());
        existing.setPassword(user.getPassword());
        existing.setFirstName(user.getFirstName());
        existing.setLastName(user.getLastName());
        existing.setPhoneNumber(user.getPhoneNumber());
        existing.setRole(user.getRole());
        existing.setStatus(user.getStatus());
        userRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void verifyEmail(String token) {
        User user = userRepository.findByConfirmationToken(token)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "Token invalide"));

        user.setEnabled(true);
        user.setConfirmationToken(null);
        userRepository.save(user);
    }

}

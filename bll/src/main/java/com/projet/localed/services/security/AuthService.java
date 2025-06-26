package com.projet.localed.services.security;

import com.projet.localed.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    void register(User user);
    User login(String email, String password);
    User findByEmail(String email);

}

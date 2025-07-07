package com.projet.localed.controllers.auth;


import com.projet.localed.entities.User;
import com.projet.localed.models.security.dtos.UserSessionDTO;
import com.projet.localed.models.security.dtos.UserTokenDTO;
import com.projet.localed.models.security.forms.LoginForm;
import com.projet.localed.models.security.forms.RegisterForm;
import com.projet.localed.services.security.AuthService;
import com.projet.localed.utils.jwt.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin("http://localhost:4200")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PreAuthorize("isAnonymous()")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterForm form) {
        authService.register(form.toUser());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<UserTokenDTO> login(@Valid @RequestBody LoginForm form, HttpServletResponse response) {
        // Authentifie l’utilisateur (bll)
        User user = authService.login(form.email(), form.password());

        // Création du DTO pour le front
        UserSessionDTO session = UserSessionDTO.fromUser(user);

        // Génère le token ici (infrastructure)
        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getId(), user.getRole().name());

        // Génère aussi le refresh token
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getId());

        // Envoie le refresh token dans un cookie HTTP-only
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(30 * 24 * 60 * 60); // 30 jours
        response.addCookie(cookie);

        // Envoie le DTO avec l’access token au front
        return ResponseEntity.ok(new UserTokenDTO(session, accessToken));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<UserTokenDTO> refreshToken(HttpServletRequest request, HttpServletResponse response) {

        // 1. Récupérer le cookie refresh_token
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refresh_token".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        if (refreshToken == null || !jwtUtil.isValid(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 2. Extraire l’email
        String email = jwtUtil.extractUsername(refreshToken);

        // 3. Retrouver l'utilisateur (via AuthService)
        User user = authService.findByEmail(email);

        // 4. Générer nouveau access token
        String newAccessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getId(), user.getRole().name());

        // 5. Générer nouveau refresh token et renvoyer en cookie
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getId());
        Cookie newCookie = new Cookie("refresh_token", newRefreshToken);
        newCookie.setHttpOnly(true);
        newCookie.setPath("/");
        newCookie.setMaxAge(30 * 24 * 60 * 60);
        response.addCookie(newCookie);

        // 6. Renvoyer nouveau token
        UserSessionDTO session = UserSessionDTO.fromUser(user);
        return ResponseEntity.ok(new UserTokenDTO(session, newAccessToken));
    }

    @GetMapping("/verify-email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void verifyEmail(@RequestParam String token) {
        authService.verifyEmail(token);
    }



}
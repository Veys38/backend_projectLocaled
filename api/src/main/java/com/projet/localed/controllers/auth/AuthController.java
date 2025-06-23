package com.projet.localed.controllers.auth;


import com.projet.localed.entities.User;
import com.projet.localed.models.security.dtos.UserSessionDTO;
import com.projet.localed.models.security.dtos.UserTokenDTO;
import com.projet.localed.models.security.forms.LoginForm;
import com.projet.localed.models.security.forms.RegisterForm;
import com.projet.localed.services.security.AuthService;
import com.projet.localed.utils.jwt.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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

    @PreAuthorize("isAnonymous()")
    @PostMapping("/login")
    public ResponseEntity<UserTokenDTO> login(@Valid @RequestBody LoginForm form) {
        User user = authService.login(form.email(), form.password());
        String token = jwtUtil.generateAccessToken(user.getEmail(), user.getId(), user.getRole().name());
        UserSessionDTO session = UserSessionDTO.fromUser(user);
        return ResponseEntity.ok(new UserTokenDTO(session, token));
    }
}
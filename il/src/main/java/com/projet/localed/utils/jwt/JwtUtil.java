package com.projet.localed.utils.jwt;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey key;
    private JwtParser parser;

    private final long accessExpiration = 1000 * 60 * 60 * 24; // 24h
    private final long refreshExpiration = 1000L * 60 * 60 * 24 * 30; // 30j

    @PostConstruct
    public void init() {
        key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        parser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String generateAccessToken(String email, Long id, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("id", id)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String email, Long id) {
        return Jwts.builder()
                .setSubject(email)
                .claim("id", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(key)
                .compact();
    }


    public boolean isValid(String token) {
        try {
            Claims claims = parser.parseClaimsJws(token).getBody();
            return claims.getExpiration().after(new Date());
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return parser.parseClaimsJws(token).getBody().getSubject();
    }

    public Long getUserId(String token) {
        return parser.parseClaimsJws(token).getBody().get("id", Long.class);
    }

    public String getRole(String token) {
        return parser.parseClaimsJws(token).getBody().get("role", String.class);
    }
}

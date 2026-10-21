package com.sunhom.auth.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sunhom.auth.config.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtConfig jwtConfig;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(
                jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(UUID userId, List<String> roles) {

        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("roles", roles)
                .setIssuer(jwtConfig.getIssuer())
                .setIssuedAt(Date.from(now))
                .setExpiration(
                        Date.from(now.plusSeconds(jwtConfig.getAccessTokenExpiry())))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims validateToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public UUID extractUserId(String token) {
        return UUID.fromString(validateToken(token).getSubject());
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return validateToken(token).get("roles", List.class);
    }
}

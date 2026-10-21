package com.sunhom.auth.service;

import java.net.InetAddress;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunhom.auth.dto.LoginRequest;
import com.sunhom.auth.dto.LoginResponse;
import com.sunhom.auth.entity.User;
import com.sunhom.auth.entity.UserSession;
import com.sunhom.auth.repository.UserRepository;
import com.sunhom.auth.repository.UserSessionRepository;

import org.springframework.security.access.AccessDeniedException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

        private final UserRepository userRepository;
        private final UserSessionRepository userSessionRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;

        public LoginResponse login(LoginRequest request, HttpServletRequest httpRequest) {

                User user = userRepository
                                .findByEmailOrMobile(request.getIdentifier(), request.getIdentifier())
                                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

                if (!user.isActive()) {
                        throw new AccessDeniedException("User is inactive");
                }

                if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {

                        throw new BadCredentialsException("Invalid credentials");
                }

                List<String> roles = user.getUserRoles()
                                .stream()
                                .map(ur -> "ROLE_" + ur.getRole().getRoleName())
                                .toList();

                String accessToken = jwtService.generateAccessToken(
                                user.getUserId(),
                                roles);
                String refreshToken = UUID.randomUUID().toString();
                InetAddress ipAddress = resolveClientIp(httpRequest);

                UserSession session = UserSession.builder()
                                .user(user)
                                .status("ACTIVE")
                                .deviceType("WEB")
                                .deviceId("WEB-" + UUID.randomUUID())
                                .refreshToken(refreshToken)
                                .createdAt(OffsetDateTime.now())
                                .expiresAt(OffsetDateTime.now().plusDays(7))
                                .ipAddress(ipAddress) // ✅ inet-compatible
                                .userAgent(httpRequest.getHeader("User-Agent"))
                                .build();

                userSessionRepository.save(session);

                return new LoginResponse(
                                user.getUserId(),
                                roles,
                                accessToken, refreshToken);
        }

        private InetAddress resolveClientIp(HttpServletRequest request) {
                try {
                        String forwarded = request.getHeader("X-Forwarded-For");
                        String ip = (forwarded != null && !forwarded.isBlank())
                                        ? forwarded.split(",")[0].trim()
                                        : request.getRemoteAddr();

                        return InetAddress.getByName(ip);
                } catch (Exception ex) {
                        return null; // NEVER break login due to IP resolution
                }
        }

        @Transactional
        public record RefreshResult(UUID userId, List<String> roles, String accessToken) {
        }

        public RefreshResult refresh(String refreshToken) {

                UserSession session = userSessionRepository
                                .findByRefreshToken(refreshToken)
                                .orElseThrow(() -> new BadCredentialsException("Invalid refresh token"));

                if (!"ACTIVE".equals(session.getStatus())
                                || session.getExpiresAt().isBefore(OffsetDateTime.now())) {
                        throw new BadCredentialsException("Session expired");
                }

                User user = session.getUser();

                List<String> roles = user.getUserRoles()
                                .stream()
                                .map(ur -> "ROLE_" + ur.getRole().getRoleName())
                                .toList();

                String newAccessToken = jwtService.generateAccessToken(
                                user.getUserId(), roles);

                session.setLastUsedAt(OffsetDateTime.now());
                userSessionRepository.save(session);

                return new RefreshResult(user.getUserId(), roles, newAccessToken);
        }

        @Transactional
        public void logout(String refreshToken) {
                userSessionRepository.findByRefreshToken(refreshToken)
                                .ifPresent(session -> {
                                        session.setStatus("REVOKED");
                                        session.setRevokedReason("USER_LOGOUT");
                                });
        }

}

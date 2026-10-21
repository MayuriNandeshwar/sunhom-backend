package com.sunhom.auth.service;

import com.sunhom.auth.dto.CustomerAuthResponse;
import com.sunhom.auth.dto.CustomerRegisterRequest;
import com.sunhom.auth.dto.ForgotPasswordRequest;
import com.sunhom.auth.dto.LoginRequest;
import com.sunhom.auth.dto.LoginResponse;
import com.sunhom.auth.dto.VerifyEmailRequest;
import com.sunhom.auth.entity.PasswordResetToken;
import com.sunhom.auth.entity.Role;
import com.sunhom.auth.entity.User;
import com.sunhom.auth.entity.UserRole;
import com.sunhom.auth.entity.UserSession;
import com.sunhom.auth.repository.PasswordResetTokenRepository;
import com.sunhom.auth.repository.RoleRepository;
import com.sunhom.auth.repository.UserRepository;
import com.sunhom.auth.repository.UserRoleRepository;
import com.sunhom.auth.repository.UserSessionRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CustomerAuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserSessionRepository userSessionRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Transactional
    public CustomerAuthResponse register(CustomerRegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        var existingUserOpt = userRepository.findByEmail(email);
        if (existingUserOpt.isPresent()) {
            User existing = existingUserOpt.get();
            if (!existing.isActive()) {
                // If user registered earlier but did not verify email, generate a fresh code and resend email
                String newCode = String.format("%06d", new SecureRandom().nextInt(1000000));
                PasswordResetToken verifyToken = PasswordResetToken.builder()
                        .user(existing)
                        .token("VERIFY-" + newCode)
                        .expiresAt(OffsetDateTime.now().plusMinutes(15))
                        .used(false)
                        .createdAt(OffsetDateTime.now())
                        .build();
                passwordResetTokenRepository.save(verifyToken);
                emailService.sendVerificationEmail(email, request.getName(), newCode);

                return CustomerAuthResponse.builder()
                        .success(true)
                        .message("Account already registered but not verified. A new 6-digit verification code has been sent to your email.")
                        .email(email)
                        .name(request.getName())
                        .requiresVerification(true)
                        .build();
            } else {
                throw new IllegalArgumentException("An account with this email already exists. Please sign in.");
            }
        }

        Role customerRole = roleRepository.findByRoleName("CUSTOMER")
                .orElseGet(() -> roleRepository.save(Role.builder()
                        .roleName("CUSTOMER")
                        .description("Customer user account")
                        .createdAt(OffsetDateTime.now())
                        .build()));

        // Crucial: Account starts as INACTIVE until email is verified!
        User user = User.builder()
                .email(email)
                .mobile(request.getMobile() != null && !request.getMobile().isBlank() ? request.getMobile().trim() : null)
                .password(passwordEncoder.encode(request.getPassword()))
                .isActive(false)
                .build();

        user = userRepository.save(user);

        UserRole userRole = UserRole.builder()
                .user(user)
                .role(customerRole)
                .assignedAt(OffsetDateTime.now())
                .build();
        userRoleRepository.save(userRole);

        // Generate 6-digit email verification code
        String code = String.format("%06d", new SecureRandom().nextInt(1000000));
        PasswordResetToken verifyToken = PasswordResetToken.builder()
                .user(user)
                .token("VERIFY-" + code)
                .expiresAt(OffsetDateTime.now().plusMinutes(15))
                .used(false)
                .createdAt(OffsetDateTime.now())
                .build();
        passwordResetTokenRepository.save(verifyToken);

        // Send Real Verification Email via EmailService
        emailService.sendVerificationEmail(email, request.getName(), code);

        return CustomerAuthResponse.builder()
                .success(true)
                .message("Account created! A 6-digit verification code has been sent to your email. Please verify to continue.")
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(request.getName())
                .roles(List.of("ROLE_CUSTOMER"))
                .requiresVerification(true)
                .build();
    }

    @Transactional
    public CustomerAuthResponse verifyEmail(VerifyEmailRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        String code = request.getCode().trim();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No account found with this email."));

        PasswordResetToken token = passwordResetTokenRepository
                .findByTokenAndUsedFalse("VERIFY-" + code)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired verification code."));

        if (!token.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("Verification code does not match this account.");
        }

        if (token.getExpiresAt().isBefore(OffsetDateTime.now())) {
            throw new IllegalArgumentException("Verification code has expired. Please request a new one.");
        }

        token.setUsed(true);
        passwordResetTokenRepository.save(token);

        // ACTIVATE USER ACCOUNT
        user.setActive(true);
        userRepository.save(user);

        // Send welcome email
        emailService.sendWelcomeEmail(email, null);

        return CustomerAuthResponse.builder()
                .success(true)
                .message("Email verified successfully! You can now sign in with your credentials.")
                .email(email)
                .requiresVerification(false)
                .build();
    }

    @Transactional
    public CustomerAuthResponse resendVerification(String email) {
        email = email.trim().toLowerCase();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No account found with this email."));

        if (user.isActive()) {
            return CustomerAuthResponse.builder()
                    .success(true)
                    .message("Your email is already verified. Please sign in.")
                    .email(email)
                    .requiresVerification(false)
                    .build();
        }

        String code = String.format("%06d", new SecureRandom().nextInt(1000000));
        PasswordResetToken verifyToken = PasswordResetToken.builder()
                .user(user)
                .token("VERIFY-" + code)
                .expiresAt(OffsetDateTime.now().plusMinutes(15))
                .used(false)
                .createdAt(OffsetDateTime.now())
                .build();
        passwordResetTokenRepository.save(verifyToken);

        emailService.sendVerificationEmail(email, null, code);

        return CustomerAuthResponse.builder()
                .success(true)
                .message("A fresh 6-digit verification code has been sent to your email.")
                .email(email)
                .requiresVerification(true)
                .build();
    }

    public LoginResponse login(LoginRequest request, HttpServletRequest httpRequest) {
        String identifier = request.getIdentifier().trim().toLowerCase();

        User user = userRepository.findByEmailOrMobile(identifier, identifier)
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password.");
        }

        if (!user.isActive()) {
            // Auto dispatch fresh verification code if unverified user tries to login
            String newCode = String.format("%06d", new SecureRandom().nextInt(1000000));
            PasswordResetToken verifyToken = PasswordResetToken.builder()
                    .user(user)
                    .token("VERIFY-" + newCode)
                    .expiresAt(OffsetDateTime.now().plusMinutes(15))
                    .used(false)
                    .createdAt(OffsetDateTime.now())
                    .build();
            passwordResetTokenRepository.save(verifyToken);
            emailService.sendVerificationEmail(user.getEmail(), null, newCode);

            throw new AccessDeniedException("Email not verified. A verification code has been sent to your email. Please verify before signing in.");
        }

        List<String> roleNames = userRoleRepository.findRoleNamesByUserId(user.getUserId());
        List<String> roles = (roleNames == null || roleNames.isEmpty())
                ? List.of("ROLE_CUSTOMER")
                : roleNames.stream().map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r).toList();

        String accessToken = jwtService.generateAccessToken(user.getUserId(), roles);
        String refreshToken = UUID.randomUUID().toString();
        InetAddress ipAddress = resolveClientIp(httpRequest);

        UserSession session = UserSession.builder()
                .user(user)
                .status("ACTIVE")
                .deviceType("WEB")
                .deviceId("WEB-" + UUID.randomUUID())
                .refreshToken(refreshToken)
                .createdAt(OffsetDateTime.now())
                .expiresAt(OffsetDateTime.now().plusDays(30))
                .ipAddress(ipAddress)
                .userAgent(httpRequest.getHeader("User-Agent"))
                .build();

        userSessionRepository.save(session);

        return new LoginResponse(
                user.getUserId(),
                roles,
                accessToken,
                refreshToken
        );
    }

    public CustomerAuthResponse forgotPassword(ForgotPasswordRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        userRepository.findByEmail(email).ifPresent(user -> {
            String code = String.format("%06d", new SecureRandom().nextInt(1000000));
            PasswordResetToken resetToken = PasswordResetToken.builder()
                    .user(user)
                    .token("RESET-" + code)
                    .expiresAt(OffsetDateTime.now().plusMinutes(15))
                    .used(false)
                    .createdAt(OffsetDateTime.now())
                    .build();
            passwordResetTokenRepository.save(resetToken);

            emailService.sendPasswordResetEmail(email, null, code);
        });

        return CustomerAuthResponse.builder()
                .success(true)
                .message("If an account exists with this email, password reset instructions have been sent.")
                .email(email)
                .build();
    }

    private InetAddress resolveClientIp(HttpServletRequest request) {
        try {
            String forwarded = request.getHeader("X-Forwarded-For");
            String ip = (forwarded != null && !forwarded.isBlank())
                    ? forwarded.split(",")[0].trim()
                    : request.getRemoteAddr();

            return InetAddress.getByName(ip);
        } catch (Exception ex) {
            return null;
        }
    }
}

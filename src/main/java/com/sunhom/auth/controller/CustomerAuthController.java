package com.sunhom.auth.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sunhom.auth.dto.*;
import com.sunhom.auth.service.CustomerAuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth/customer")
@RequiredArgsConstructor
public class CustomerAuthController {

    private final CustomerAuthService customerAuthService;

    @PostMapping("/register")
    public ResponseEntity<CustomerAuthResponse> register(@Valid @RequestBody CustomerRegisterRequest request) {
        CustomerAuthResponse response = customerAuthService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<CustomerAuthResponse> verifyEmail(@Valid @RequestBody VerifyEmailRequest request) {
        CustomerAuthResponse response = customerAuthService.verifyEmail(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<CustomerAuthResponse> resendVerification(@RequestParam String email) {
        CustomerAuthResponse response = customerAuthService.resendVerification(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse response) {

        LoginResponse loginResponse = customerAuthService.login(request, httpRequest);

        ResponseCookie accessCookie = ResponseCookie.from("ACCESS_TOKEN", loginResponse.getAccessToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(1800)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("REFRESH_TOKEN", loginResponse.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return ResponseEntity.ok(loginResponse.withoutToken());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<CustomerAuthResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        CustomerAuthResponse response = customerAuthService.forgotPassword(request);
        return ResponseEntity.ok(response);
    }
}

package com.sunhom.auth.dto;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private UUID userId;
    private List<String> roles;

    @JsonIgnore
    private String accessToken;
    private String refreshToken;

    public LoginResponse withoutToken() {
        return new LoginResponse(userId, roles, null, null);
    }

}

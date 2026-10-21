package com.sunhom.auth.dto;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAuthResponse {
    private boolean success;
    private String message;
    private UUID userId;
    private String email;
    private String name;
    private List<String> roles;
    private boolean requiresVerification;
}

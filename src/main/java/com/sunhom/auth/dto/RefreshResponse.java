package com.sunhom.auth.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshResponse {
    private UUID userId;
    private List<String> roles;
}

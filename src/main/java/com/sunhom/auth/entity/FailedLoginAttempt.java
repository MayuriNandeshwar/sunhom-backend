package com.sunhom.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.net.InetAddress;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "failed_login_attempts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FailedLoginAttempt {

    @Id
    @GeneratedValue
    @Column(name = "attempt_id", columnDefinition = "uuid")
    private UUID attemptId;

    private String email;
    private String mobile;

    @Column(name = "ip_address")
    private InetAddress ipAddress;

    @Column(name = "attempted_at", nullable = false)
    private OffsetDateTime attemptedAt;
}

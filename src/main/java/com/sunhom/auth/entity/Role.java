package com.sunhom.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "role_id", columnDefinition = "uuid")
    private UUID roleId;

    @Column(name = "role_name", nullable = false, unique = true, length = 100)
    private String roleName;

    @Column(length = 255)
    private String description;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}

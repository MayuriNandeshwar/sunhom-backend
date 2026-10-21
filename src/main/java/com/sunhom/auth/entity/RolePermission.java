package com.sunhom.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "role_permissions", uniqueConstraints = @UniqueConstraint(columnNames = { "role_id", "permission_id" }))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermission {

    @Id
    @GeneratedValue
    @Column(name = "role_permission_id", columnDefinition = "uuid")
    private UUID rolePermissionId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(optional = false)
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}

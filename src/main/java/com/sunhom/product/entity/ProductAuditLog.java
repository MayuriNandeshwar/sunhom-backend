package com.sunhom.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "product_audit_log", indexes = {
        @Index(name = "idx_product_audit_product", columnList = "product_id"),
        @Index(name = "idx_product_audit_action", columnList = "action"),
        @Index(name = "idx_product_audit_created_at", columnList = "created_at")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAuditLog {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(nullable = false, length = 50)
    private String action; // CREATED / UPDATED / PUBLISHED

    @Column(name = "performed_by")
    private UUID performedBy;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}

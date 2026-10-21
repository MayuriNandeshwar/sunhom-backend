package com.sunhom.inventory.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "product_stock_log")
public class ProductStockLog {

    @Id
    @GeneratedValue
    @Column(name = "log_id", columnDefinition = "uuid")
    private UUID logId;

    @Column(name = "variant_id", nullable = false)
    private UUID variantId;

    @Column(name = "change_type", nullable = false, length = 30)
    private String changeType;

    @Column(name = "change_amount", nullable = false)
    private int changeAmount;

    @Column(length = 255)
    private String note;

    @Column(name = "changed_by")
    private UUID changedBy;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = OffsetDateTime.now();
    }

    /* getters */
}

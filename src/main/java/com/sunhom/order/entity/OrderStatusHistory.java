package com.sunhom.order.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "order_status_history")
public class OrderStatusHistory {

    @Id
    @GeneratedValue
    @Column(name = "history_id", columnDefinition = "uuid")
    private UUID historyId;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(length = 255)
    private String note;

    @Column(name = "changed_by")
    private UUID changedBy;

    @Column(name = "changed_at", nullable = false)
    private OffsetDateTime changedAt;

    @PrePersist
    void onCreate() {
        this.changedAt = OffsetDateTime.now();
    }

    /* getters */
}

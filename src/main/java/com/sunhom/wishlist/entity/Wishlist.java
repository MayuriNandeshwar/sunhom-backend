package com.sunhom.wishlist.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "wishlists", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "product_id" }))
public class Wishlist {

    @Id
    @GeneratedValue
    @Column(name = "wishlist_id", columnDefinition = "uuid")
    private UUID wishlistId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = OffsetDateTime.now();
    }

    /* getters */
}

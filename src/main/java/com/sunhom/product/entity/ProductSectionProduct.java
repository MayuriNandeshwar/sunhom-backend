package com.sunhom.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "product_section_products", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "section_id", "product_id" })
}, indexes = {
        @Index(name = "idx_psp_section", columnList = "section_id"),
        @Index(name = "idx_psp_product", columnList = "product_id"),
        @Index(name = "idx_psp_section_active_order", columnList = "section_id, is_active, display_order")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSectionProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_section_products_id", updatable = false, nullable = false)
    private UUID productSectionProductsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private ProductSection section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Products products;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

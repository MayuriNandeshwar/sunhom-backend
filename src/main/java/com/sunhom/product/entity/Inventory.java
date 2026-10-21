package com.sunhom.product.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue
    @Column(name = "inventory_id", columnDefinition = "uuid")
    private UUID inventoryId;

    @OneToOne(optional = false)
    @JoinColumn(name = "variant_id", nullable = false, unique = true)
    private ProductVariants productVariants;

    @Column(name = "available_quantity", nullable = false)
    private int availableQuantity;

    @Column(name = "reserved_quantity", nullable = false)
    private int reservedQuantity;

    @Column(name = "low_stock_threshold", nullable = false)
    private int lowStockThreshold;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}

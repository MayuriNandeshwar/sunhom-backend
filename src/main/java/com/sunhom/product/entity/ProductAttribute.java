
package com.sunhom.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "product_attributes", uniqueConstraints = {
        @UniqueConstraint(columnNames = "code")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "attribute_id", updatable = false, nullable = false)
    private UUID attributeId;

    /**
     * Stable backend identifier
     * Example: WAX_TYPE, BURN_TIME
     * NEVER change once created
     */
    @Column(nullable = false, length = 50)
    private String code;

    /**
     * Human-readable label
     * Example: "Wax Type"
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * TEXT | NUMBER | BOOLEAN | ENUM
     * Used for validation + UI rendering
     */
    @Column(name = "data_type", nullable = false, length = 20)
    private String dataType;

    /**
     * Controls filter visibility on listing pages
     */
    @Column(name = "is_filterable", nullable = false)
    private Boolean isFilterable = false;

    /**
     * Mandatory attribute before product publish
     */
    @Column(name = "is_required", nullable = false)
    private Boolean isRequired = false;

    /**
     * Soft disable (never delete attributes)
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @PrePersist
    void onCreate() {
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }
}
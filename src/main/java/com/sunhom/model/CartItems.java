package com.sunhom.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.sunhom.product.entity.ProductVariants;

@Entity
@Table(name = "cart_items", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "cart_id", "variant_id" })
})
@Getter
@Setter
@NoArgsConstructor
public class CartItems {

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id", columnDefinition = "uuid")
    private UUID cartItemId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(optional = false)
    @JoinColumn(name = "variant_id", nullable = false)
    private ProductVariants productVariants;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "price_at_add", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceAtAdd;

    @Column(name = "added_at", nullable = false)
    private OffsetDateTime addedAt;
}

package com.sunhom.inventory.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminInventoryItemDto {
    private UUID inventoryId;
    private UUID variantId;
    private UUID productId;
    private String productName;
    private String sku;
    private String variantTitle;
    private String fragrance;
    private String color;
    private String sizeLabel;
    private Integer weightGrams;
    private BigDecimal price;
    private String thumbnail;
    private int availableQuantity;
    private int reservedQuantity;
    private int lowStockThreshold;
    private String stockStatus; // IN_STOCK, LOW_STOCK, OUT_OF_STOCK
    private OffsetDateTime updatedAt;
}

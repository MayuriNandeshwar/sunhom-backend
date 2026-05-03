package com.sunhom.product.dto.publicapi;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductListingDto(
        UUID productId,
        String productName,
        String slug,
        String shortDescription,
        String sku,
        BigDecimal price,
        BigDecimal mrp,
        BigDecimal discountPercentage,
        String imageUrl,
        boolean inStock,
        String categorySlug) {
}
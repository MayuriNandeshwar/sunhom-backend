package com.sunhom.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Bestseller product DTO
 * Version: v1.1
 * Added: shortDescription
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BestsellerProductDto {

    @JsonProperty("productId")
    private UUID productId;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("shortDescription")
    private String shortDescription;

    @JsonProperty("sku")
    private String sku;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("mrp")
    private BigDecimal mrp;

    @JsonProperty("discountPercentage")
    private BigDecimal discountPercentage;

    @JsonProperty("imageUrl")
    private String imageUrl;

    @JsonProperty("hoverImageUrl")
    private String hoverImageUrl;

    @JsonProperty("inStock")
    private Boolean inStock;

    @JsonProperty("category")
    private String category;

    @JsonProperty("categorySlug")
    private String categorySlug;
}
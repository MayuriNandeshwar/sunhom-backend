package com.sunhom.product.dto.publicapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewArrivalProductDto {

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
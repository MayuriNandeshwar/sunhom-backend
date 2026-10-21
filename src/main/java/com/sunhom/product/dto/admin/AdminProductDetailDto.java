package com.sunhom.product.dto.admin;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminProductDetailDto {

    private UUID productId;
    private String name;
    private String slug;
    private String description;
    private String status;

    private List<VariantDto> variants;
    private List<AttributeDto> attributes;
    private List<ImageDto> images;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VariantDto {
        private UUID variantId;
        private String title;
        private BigDecimal price;
        private BigDecimal mrp;
        private Integer weightGrams;
        private boolean isDefault;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AttributeDto {
        private String name;
        private String value;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImageDto {
        private UUID imageId;
        private String url;
        private Integer position;
        private boolean isPrimary;
    }
}

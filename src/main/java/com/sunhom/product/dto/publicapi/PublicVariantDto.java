package com.sunhom.product.dto.publicapi;

import java.util.UUID;

import lombok.*;

@Getter
@Setter
@Builder
public class PublicVariantDto {

    private UUID variantId;
    private String title;
    private String sku;
    private double price;
    private Double mrp;
    private boolean isDefault;
    private Integer weightGrams;
}

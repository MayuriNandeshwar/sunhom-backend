package com.sunhom.product.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
public class AdminVariantCreateDto {

    @NotBlank
    private String sku;

    @NotNull
    private BigDecimal price;

    private BigDecimal mrp;
    private String variantTitle;
    private Integer weightGrams;

    private boolean isDefault;
}

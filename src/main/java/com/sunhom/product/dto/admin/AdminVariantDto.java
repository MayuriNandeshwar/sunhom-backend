package com.sunhom.product.dto.admin;

import java.math.BigDecimal;
import java.util.*;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AdminVariantDto {
    private UUID variantId;
    private String variantTitle;
    private String fragrance;
    private Integer weightGrams;
    private String sizeLabel;
    private Integer durationHours;

    private BigDecimal price;
    private BigDecimal mrp;

    private boolean isActive;
    private boolean isDefault;
}

package com.sunhom.product.dto.admin;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminProductUpdateDto {

    private String productName;
    private String shortDescription;
    private String productDescription;
    private String brand;
    private String searchKeywords;

    private boolean isActive;
    private boolean isFeatured;
    private boolean isManualBestseller;

    private String seoTitle;
    private String seoDescription;
    private String seoKeywords;

    private BigDecimal defaultPrice;
    private BigDecimal defaultMrp;

    private List<AdminProductAttributeDto> attributes;
}

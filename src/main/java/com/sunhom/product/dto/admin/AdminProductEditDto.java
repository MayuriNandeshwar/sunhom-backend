package com.sunhom.product.dto.admin;

import lombok.*;
import java.util.*;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminProductEditDto {

    private UUID productId;
    private String productName;
    private String slug;
    private String skuBase;

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

    private UUID categoryId;
    private UUID productTypeId;

    private List<AdminVariantDto> variants;
    private List<AdminProductAttributeDto> attributes;
    private List<AdminProductImageDto> images;
}

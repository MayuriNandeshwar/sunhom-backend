package com.sunhom.product.mapper;

import com.sunhom.product.dto.SectionProductResponseDto;
import com.sunhom.product.entity.ProductSectionProduct;

public class SectionProductMapper {

    public static SectionProductResponseDto toResponse(ProductSectionProduct entity) {
        return SectionProductResponseDto.builder()
                .id(entity.getProductSectionProductsId())
                .sectionId(entity.getSection().getSectionId())
                .productId(entity.getProducts().getProductId())
                .displayOrder(entity.getDisplayOrder())
                .isActive(entity.getIsActive())
                .build();
    }
}

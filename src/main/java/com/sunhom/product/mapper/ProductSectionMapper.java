package com.sunhom.product.mapper;

import com.sunhom.product.dto.ProductSectionRequestDto;
import com.sunhom.product.dto.ProductSectionResponseDto;
import com.sunhom.product.entity.ProductSection;

public class ProductSectionMapper {

    public static ProductSection toEntity(ProductSectionRequestDto dto) {
        return ProductSection.builder()
                .code(dto.getCode())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .displayOrder(dto.getDisplayOrder())
                .isActive(dto.getIsActive())
                .build();
    }

    public static ProductSectionResponseDto toResponse(ProductSection entity) {
        return ProductSectionResponseDto.builder()
                .sectionId(entity.getSectionId())
                .code(entity.getCode())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .displayOrder(entity.getDisplayOrder())
                .isActive(entity.getIsActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

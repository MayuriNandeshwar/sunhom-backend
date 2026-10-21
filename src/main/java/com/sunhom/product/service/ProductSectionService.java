package com.sunhom.product.service;

import java.util.List;
import java.util.UUID;

import com.sunhom.product.dto.ProductSectionRequestDto;
import com.sunhom.product.dto.ProductSectionResponseDto;

public interface ProductSectionService {

    ProductSectionResponseDto create(ProductSectionRequestDto dto);

    ProductSectionResponseDto update(UUID sectionId, ProductSectionRequestDto dto);

    List<ProductSectionResponseDto> getAll();

    List<ProductSectionResponseDto> getActiveSections();
}

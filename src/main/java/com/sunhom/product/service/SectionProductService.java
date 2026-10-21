package com.sunhom.product.service;

import java.util.List;
import java.util.UUID;

import com.sunhom.product.dto.SectionProductRequestDto;
import com.sunhom.product.dto.SectionProductResponseDto;

public interface SectionProductService {

    SectionProductResponseDto addProductToSection(SectionProductRequestDto dto);

    List<SectionProductResponseDto> getProductsBySection(UUID sectionId);
}

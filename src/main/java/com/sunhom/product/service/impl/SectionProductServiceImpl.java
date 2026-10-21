package com.sunhom.product.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sunhom.product.dto.SectionProductRequestDto;
import com.sunhom.product.dto.SectionProductResponseDto;
import com.sunhom.product.entity.ProductSection;
import com.sunhom.product.entity.ProductSectionProduct;
import com.sunhom.product.entity.Products;
import com.sunhom.product.mapper.SectionProductMapper;
import com.sunhom.product.repository.ProductRepository;
import com.sunhom.product.repository.ProductSectionProductRepository;
import com.sunhom.product.repository.ProductSectionRepository;
import com.sunhom.product.service.SectionProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SectionProductServiceImpl implements SectionProductService {

        private final ProductSectionRepository sectionRepo;
        private final ProductRepository productRepo;
        private final ProductSectionProductRepository mappingRepo;

        @Override
        public SectionProductResponseDto addProductToSection(SectionProductRequestDto dto) {

                if (mappingRepo.existsBySection_SectionIdAndProducts_ProductId(
                                dto.getSectionId(), dto.getProductId())) {
                        throw new RuntimeException("Product already added to this section");
                }

                ProductSection section = sectionRepo.findById(dto.getSectionId())
                                .orElseThrow(() -> new RuntimeException("Section not found"));

                Products product = productRepo.findById(dto.getProductId())
                                .orElseThrow(() -> new RuntimeException("Product not found"));

                ProductSectionProduct mapping = ProductSectionProduct.builder()
                                .section(section)
                                .products(product)
                                .displayOrder(dto.getDisplayOrder())
                                .isActive(dto.getIsActive())
                                .build();

                return SectionProductMapper.toResponse(mappingRepo.save(mapping));
        }

        @Override
        public List<SectionProductResponseDto> getProductsBySection(UUID sectionId) {
                return mappingRepo
                                .findBySection_SectionIdAndIsActiveTrueOrderByDisplayOrderAsc(sectionId)
                                .stream()
                                .map(SectionProductMapper::toResponse)
                                .toList();
        }
}

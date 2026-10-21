package com.sunhom.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.sunhom.product.dto.ProductSectionRequestDto;
import com.sunhom.product.dto.ProductSectionResponseDto;
import com.sunhom.product.entity.ProductSection;
import com.sunhom.product.mapper.ProductSectionMapper;
import com.sunhom.product.repository.ProductSectionRepository;
import com.sunhom.product.service.ProductSectionService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductSectionServiceImpl implements ProductSectionService {

    private final ProductSectionRepository repository;

    @Override
    public ProductSectionResponseDto create(ProductSectionRequestDto dto) {

        if (repository.existsByCode(dto.getCode())) {
            throw new RuntimeException("Product section code already exists");
        }

        ProductSection section = ProductSectionMapper.toEntity(dto);
        ProductSection saved = repository.save(section);

        return ProductSectionMapper.toResponse(saved);
    }

    @Override
    public ProductSectionResponseDto update(UUID sectionId, ProductSectionRequestDto dto) {

        ProductSection section = repository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Product section not found"));

        section.setTitle(dto.getTitle());
        section.setDescription(dto.getDescription());
        section.setDisplayOrder(dto.getDisplayOrder());
        section.setIsActive(dto.getIsActive());

        return ProductSectionMapper.toResponse(repository.save(section));
    }

    @Override
    public List<ProductSectionResponseDto> getAll() {
        return repository.findAll()
                .stream()
                .map(ProductSectionMapper::toResponse)
                .toList();
    }

    @Override
    public List<ProductSectionResponseDto> getActiveSections() {
        return repository.findByIsActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(ProductSectionMapper::toResponse)
                .toList();
    }
}

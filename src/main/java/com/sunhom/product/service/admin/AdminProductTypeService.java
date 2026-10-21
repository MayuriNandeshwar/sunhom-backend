package com.sunhom.product.service.admin;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunhom.product.dto.admin.*;
import com.sunhom.product.entity.ProductTypes;
import com.sunhom.product.repository.ProductTypeRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminProductTypeService {

    private final ProductTypeRepository repository;

    public UUID create(ProductTypeCreateDto dto) {

        if (repository.existsByProductTypeName(dto.getProductTypeName())) {
            throw new IllegalArgumentException("Product type already exists");
        }

        ProductTypes type = ProductTypes.builder()
                .productTypeName(dto.getProductTypeName())
                .productTypeDescription(dto.getProductTypeDescription())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        repository.save(type);

        return type.getProductTypeId();
    }

    public List<ProductTypeResponseDto> getAll() {
        return repository.findAll().stream()
                .map(this::map)
                .toList();
    }

    public ProductTypeResponseDto getById(UUID id) {
        ProductTypes type = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product type not found"));

        return map(type);
    }

    public void update(UUID id, ProductTypeUpdateDto dto) {

        ProductTypes type = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product type not found"));

        type.setProductTypeName(dto.getProductTypeName());
        type.setProductTypeDescription(dto.getProductTypeDescription());
        type.setUpdatedAt(OffsetDateTime.now());
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private ProductTypeResponseDto map(ProductTypes t) {
        return ProductTypeResponseDto.builder()
                .productTypeId(t.getProductTypeId())
                .productTypeName(t.getProductTypeName())
                .productTypeDescription(t.getProductTypeDescription())
                .createdAt(t.getCreatedAt())
                .updatedAt(t.getUpdatedAt())
                .build();
    }
}

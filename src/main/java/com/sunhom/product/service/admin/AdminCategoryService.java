package com.sunhom.product.service.admin;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunhom.product.dto.admin.*;
import com.sunhom.product.entity.Categories;
import com.sunhom.product.repository.CategoryRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCategoryService {

    private final CategoryRepository repository;

    public List<CategoryResponseDto> getAll() {
        return repository.findByDeletedAtIsNull()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public CategoryResponseDto getById(UUID id) {
        Categories category = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        return mapToDto(category);
    }

    public UUID create(CategoryCreateDto dto) {

        if (repository.existsBySlug(dto.getSlug())) {
            throw new IllegalArgumentException("Slug already exists");
        }

        Categories parent = null;
        if (dto.getParentId() != null) {
            parent = repository.findById(dto.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent not found"));
        }

        Categories category = Categories.builder()
                .categoryName(dto.getCategoryName())
                .slug(dto.getSlug())
                .categoryDescription(dto.getCategoryDescription())
                .imageUrl(dto.getImageUrl())
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : true)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .parent(parent)
                .build();

        repository.save(category);

        return category.getCategoryId();
    }

    public void update(UUID id, CategoryUpdateDto dto) {

        Categories category = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        category.setCategoryName(dto.getCategoryName());
        category.setSlug(dto.getSlug());
        category.setCategoryDescription(dto.getCategoryDescription());
        category.setImageUrl(dto.getImageUrl());
        category.setActive(dto.getIsActive() != null ? dto.getIsActive() : true);

        if (dto.getParentId() != null) {
            Categories parent = repository.findById(dto.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent not found"));
            category.setParent(parent);
        } else {
            category.setParent(null);
        }

        category.setUpdatedAt(OffsetDateTime.now());

        repository.save(category);
    }

    public void delete(UUID id) {
        Categories category = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        category.setDeletedAt(OffsetDateTime.now());
        repository.save(category);
    }

    private CategoryResponseDto mapToDto(Categories category) {
        return CategoryResponseDto.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .slug(category.getSlug())
                .categoryDescription(category.getCategoryDescription())
                .imageUrl(category.getImageUrl())
                .isActive(category.isActive())
                .parentId(category.getParent() != null ? category.getParent().getCategoryId() : null)
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}

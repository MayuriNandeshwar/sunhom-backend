package com.sunhom.product.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.sunhom.product.dto.ProductSectionRequestDto;
import com.sunhom.product.dto.ProductSectionResponseDto;
import com.sunhom.product.service.ProductSectionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/product-sections")
@RequiredArgsConstructor
public class AdminProductSectionController {

    private final ProductSectionService service;

    @PostMapping
    public ProductSectionResponseDto create(
            @Valid @RequestBody ProductSectionRequestDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{sectionId}")
    public ProductSectionResponseDto update(
            @PathVariable UUID sectionId,
            @Valid @RequestBody ProductSectionRequestDto dto) {
        return service.update(sectionId, dto);
    }

    @GetMapping
    public List<ProductSectionResponseDto> getAll() {
        return service.getAll();
    }
}

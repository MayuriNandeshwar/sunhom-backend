package com.sunhom.product.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.sunhom.product.dto.SectionProductRequestDto;
import com.sunhom.product.dto.SectionProductResponseDto;
import com.sunhom.product.service.SectionProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/section-products")
@RequiredArgsConstructor
public class AdminSectionProductController {

    private final SectionProductService service;

    @PostMapping
    public SectionProductResponseDto addProduct(
            @Valid @RequestBody SectionProductRequestDto dto) {
        return service.addProductToSection(dto);
    }

    @GetMapping("/{sectionId}")
    public List<SectionProductResponseDto> getBySection(
            @PathVariable UUID sectionId) {
        return service.getProductsBySection(sectionId);
    }
}

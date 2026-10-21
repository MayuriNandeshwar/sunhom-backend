package com.sunhom.product.controller.admin;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import com.sunhom.product.dto.admin.*;
import com.sunhom.product.service.admin.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService productService;
    private final AdminVariantService variantService;
    private final AdminProductPublishService publishService;
    private final AdminProductEditService editService;

    // CREATE PRODUCT
    @PostMapping
    public UUID createProduct(@RequestBody @Valid AdminProductCreateDto dto) {
        return productService.createProduct(dto);
    }

    @GetMapping
    public List<AdminProductListDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public AdminProductDetailDto getProduct(@PathVariable UUID productId) {
        return productService.getProduct(productId);
    }

    // ADD VARIANT
    @PostMapping("/{productId}/variants")
    public void addVariant(
            @PathVariable UUID productId,
            @RequestBody @Valid AdminVariantCreateDto dto) {

        variantService.addVariant(productId, dto);
    }

    // PUBLISH PRODUCT
    @PutMapping("/{productId}/publish")
    public void publish(@PathVariable UUID productId) {
        publishService.publish(productId);
    }

    // GET PRODUCT FOR EDIT
    @GetMapping("/{productId}/edit")
    public AdminProductEditDto getForEdit(@PathVariable UUID productId) {
        return editService.getProductForEdit(productId);
    }

    // UPDATE PRODUCT (DRAFT)
    @PutMapping("/{productId}")
    public void updateProduct(
            @PathVariable UUID productId,
            @RequestBody @Valid AdminProductUpdateDto dto) {

        editService.updateProduct(productId, dto);
    }

    // @PutMapping("/{productId}/variants/{variantId}")
    // public void updateVariant(
    // @PathVariable UUID productId,
    // @PathVariable UUID variantId,
    // @RequestBody @Valid AdminVariantUpdateDto dto) {
    // variantService.update(productId, variantId, dto);
    // }

}
package com.sunhom.product.controller.admin;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunhom.product.dto.ProductAttributeAssignDto;
import com.sunhom.product.service.ProductEntityAttributeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductEntityAttributeController {

    private final ProductEntityAttributeService service;

    @PostMapping("/{productId}/attributes")
    public void assign(
            @PathVariable UUID productId,
            @RequestBody List<ProductAttributeAssignDto> dtos) {
        service.assignToProduct(productId, dtos);
    }
}

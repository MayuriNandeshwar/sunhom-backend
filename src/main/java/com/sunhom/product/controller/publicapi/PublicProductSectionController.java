package com.sunhom.product.controller.publicapi;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.sunhom.product.dto.ProductSectionResponseDto;
import com.sunhom.product.service.ProductSectionService;

import java.util.List;

@RestController
@RequestMapping("/api/public/product-sections")
@RequiredArgsConstructor
public class PublicProductSectionController {

    private final ProductSectionService service;

    @GetMapping
    public List<ProductSectionResponseDto> getActiveSections() {
        return service.getActiveSections();
    }
}

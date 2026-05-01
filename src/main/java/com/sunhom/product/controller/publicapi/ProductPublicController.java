package com.sunhom.product.controller.publicapi;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.sunhom.product.dto.publicapi.ProductListingDto;
import com.sunhom.product.service.publicapi.ProductPublicService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public/products")
@RequiredArgsConstructor
public class ProductPublicController {

    private final ProductPublicService service;

    @GetMapping
    public Map<String, Object> getProductsByCategory(
            @RequestParam String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean inStock,
            @RequestParam(required = false) String sort) {


        List<ProductListingDto> data = service.getProductsByCategoryAdvanced(
                category,
                minPrice,
                maxPrice,
                inStock,
                sort);

        return Map.of(
                "version", "v1.0",
                "total", data.size(),
                "data", data);
    }

    
}
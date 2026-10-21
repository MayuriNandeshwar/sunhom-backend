package com.sunhom.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sunhom.product.dto.BestsellerProductDto;
import com.sunhom.product.dto.BestsellerResponse;
import com.sunhom.product.service.BestsellerService;

import java.util.List;

/**
 * Public API for bestseller products
 * Base path: /api/public/products
 * Version: v1
 */
@RestController
@RequestMapping("/api/public/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow all origins for public API
public class BestsellerController {

    private final BestsellerService bestsellerService;

    /**
     * GET /api/public/products/bestsellers
     * Returns top 10 bestselling products
     * 
     * Response: BestsellerResponse (versioned)
     * Cache: 10 minutes (Redis)
     * 
     * @return List of bestseller products with metadata
     */
    @GetMapping("/bestsellers")
    public ResponseEntity<BestsellerResponse> getBestsellers() {

        List<BestsellerProductDto> products = bestsellerService.getBestsellers();

        BestsellerResponse response = BestsellerResponse.builder()
                .version("v1.0")
                .timestamp(System.currentTimeMillis())
                .total(products.size())
                .data(products)
                .cached(true) // Always true after first call (cached in Redis)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/public/products/bestsellers/refresh
     * Admin endpoint to manually refresh bestseller cache
     * 
     * @return Success message
     */
    @PostMapping("/bestsellers/refresh")
    public ResponseEntity<String> refreshBestsellers() {
        bestsellerService.evictBestsellersCache();
        return ResponseEntity.ok("Bestseller cache refreshed successfully");
    }
}
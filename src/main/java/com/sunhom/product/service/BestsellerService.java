package com.sunhom.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sunhom.product.dto.BestsellerProductDto;
import com.sunhom.product.repository.BestsellerRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BestsellerService {

    private final BestsellerRepository repository;

    /**
     * Get bestseller products with Redis caching
     * Cache name: bestsellers:v2
     * TTL: 10 minutes
     */
    // @Cacheable(value = "bestsellers:v2")
    public List<BestsellerProductDto> getBestsellers() {

        log.info("🔥 CACHE MISS: Fetching bestsellers from database");

        List<Object[]> rows = repository.findBestsellersRaw();

        if (rows == null || rows.isEmpty()) {
            log.info("⚠️ No bestseller data found in database");
            return List.of();
        }

        return rows.stream()
                .map(r -> new BestsellerProductDto(
                        (UUID) r[0],
                        (String) r[1],
                        (String) r[2],
                        (String) r[3],
                        (String) r[4],
                        (BigDecimal) r[5],
                        (BigDecimal) r[6],
                        (BigDecimal) r[7],
                        (String) r[8],
                        (String) r[9],
                        (Boolean) r[10],
                        (String) r[11], // category name
                        (String) r[12] // category slug
                ))
                .toList();
    }

    /**
     * Clear bestsellers cache
     */
    // @CacheEvict(value = "bestsellers:v2", allEntries = true)
    public void evictBestsellersCache() {
        log.info("🧹 Bestseller cache cleared (v2)");
    }
}
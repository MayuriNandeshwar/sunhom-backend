package com.sunhom.product.service.publicapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sunhom.product.dto.publicapi.NewArrivalProductDto;
import com.sunhom.product.repository.publicapi.NewArrivalRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewArrivalService {

    private final NewArrivalRepository repository;

    /**
     * Homepage → limit 8
     * Full page → limit 24
     */
    // @Cacheable(value = "new_arrivals:v1")
    public List<NewArrivalProductDto> getNewArrivals(int limit) {

        log.info("🔥 Fetching New Arrivals from DB (limit={})", limit);

        List<Object[]> rows = repository.findNewArrivalsRaw(limit);

        return rows.stream()
                .map(r -> new NewArrivalProductDto(
                        (UUID) r[0],
                        (String) r[1],
                        (String) r[2],
                        (String) r[3],
                        (String) r[4],
                        (BigDecimal) r[5],
                        (BigDecimal) r[6],
                        new BigDecimal(r[7].toString()),
                        (String) r[8],
                        (String) r[9],
                        (Boolean) r[10],

                        // ✅ NEW
                        (String) r[11], // category name
                        (String) r[12] // category slug
                ))
                .toList();
    }

    // @CacheEvict(value = "new_arrivals:v1", allEntries = true)
    public void evictNewArrivalsCache() {
        log.info("🧹 New Arrivals cache cleared");
    }
}
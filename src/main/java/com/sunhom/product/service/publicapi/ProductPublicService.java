package com.sunhom.product.service.publicapi;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sunhom.product.dto.publicapi.ProductListingDto;
import com.sunhom.product.repository.publicapi.ProductPublicRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductPublicService {

        private final ProductPublicRepository repository;

        // @Cacheable(value = "products_by_category:v1", key = "#categorySlug")
        public List<ProductListingDto> getProductsByCategoryAdvanced(
                        String categorySlug,
                        BigDecimal minPrice,
                        BigDecimal maxPrice,
                        Boolean inStock,
                        String sort) {

                List<Object[]> rows = repository.findProductsByCategoryAdvanced(
                                categorySlug,
                                minPrice,
                                maxPrice,
                                inStock,
                                sort);

                return rows.stream()
                                .map(r -> new ProductListingDto(
                                                (UUID) r[0],
                                                (String) r[1],
                                                (String) r[2],
                                                (String) r[3],
                                                (String) r[4],
                                                (BigDecimal) r[5],
                                                (BigDecimal) r[6],
                                                new BigDecimal(r[7].toString()),
                                                (String) r[8],
                                                (Boolean) r[9],
                                                (String) r[10]))
                                .toList();
        }
}
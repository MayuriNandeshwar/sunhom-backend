package com.sunhom.product.repository.publicapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sunhom.product.entity.Products;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductPublicRepository extends JpaRepository<Products, UUID> {

    @Query(value = """
            SELECT
                p.product_id,
                p.product_name,
                p.slug,
                p.short_description,
                pv.sku,
                pv.price,
                pv.mrp,
                CAST(
                ROUND(
                    CASE
                        WHEN pv.mrp > 0
                        THEN ((pv.mrp - pv.price) * 100 / pv.mrp)
                        ELSE 0
                    END, 2
                ) AS NUMERIC
            ),
                pi.product_image_url,
                (COALESCE(i.available_quantity, 0) > 0),
            c.slug AS category_slug
            FROM products p
            JOIN categories c ON c.category_id = p.category_id
            JOIN product_variants pv
                ON pv.product_id = p.product_id
                AND pv.is_active = true
                AND pv.is_default = true
            LEFT JOIN inventory i
                ON i.variant_id = pv.variant_id
            LEFT JOIN product_images pi
                ON pi.variant_id = pv.variant_id
                AND pi.is_primary = true
            WHERE p.is_active = true
              AND p.deleted_at IS NULL
              AND c.slug = :categorySlug
              AND (:minPrice IS NULL OR pv.price >= :minPrice)
              AND (:maxPrice IS NULL OR pv.price <= :maxPrice)
              AND (:inStock IS NULL OR
                    (:inStock = true AND COALESCE(i.available_quantity,0) > 0)
                  )
            ORDER BY
                CASE WHEN :sort = 'price_asc' THEN pv.price END ASC,
                CASE WHEN :sort = 'price_desc' THEN pv.price END DESC,
                p.created_at DESC
            """, nativeQuery = true)
    List<Object[]> findProductsByCategoryAdvanced(
            String categorySlug,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Boolean inStock,
            String sort);
}
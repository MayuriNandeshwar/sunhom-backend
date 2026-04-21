package com.sunhom.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sunhom.product.entity.Products;

import java.util.List;
import java.util.UUID;

@Repository
public interface BestsellerRepository extends JpaRepository<Products, UUID> {

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
                    ROUND(((pv.mrp - pv.price) * 100 / pv.mrp), 2)
                    AS NUMERIC
                ) AS discount_percentage,
                pi1.product_image_url AS image_url,
                pi2.product_image_url AS hover_image_url,
                (COALESCE(i.available_quantity, 0) > 0) AS in_stock,
                c.category_name,
                c.slug   -- ✅ NEW FIELD

            FROM products p

            JOIN categories c
                ON c.category_id = p.category_id

            JOIN product_variants pv
                ON pv.product_id = p.product_id
                AND pv.is_active = true
                AND pv.is_default = true

            LEFT JOIN inventory i
                ON i.variant_id = pv.variant_id

            LEFT JOIN product_images pi1
                ON pi1.variant_id = pv.variant_id
                AND pi1.is_primary = true

            LEFT JOIN product_images pi2
                ON pi2.variant_id = pv.variant_id
                AND pi2.is_hover_image = true

            LEFT JOIN order_items oi
                ON oi.variant_id = pv.variant_id

            LEFT JOIN orders o
                ON o.order_id = oi.order_id
                AND o.payment_status = 'PAID'

            WHERE p.is_active = true
            AND p.deleted_at IS NULL
            AND (
                  p.is_manual_bestseller = true
                  OR p.is_auto_bestseller = true
                )

            GROUP BY
                p.product_id,
                p.product_name,
                p.slug,
                p.short_description,
                pv.sku,
                pv.price,
                pv.mrp,
                pi1.product_image_url,
                pi2.product_image_url,
                i.available_quantity,
                c.category_name,
                c.slug   -- ✅ ADD HERE

            ORDER BY
                p.is_manual_bestseller DESC,
                COALESCE(SUM(oi.quantity), 0) DESC

            LIMIT 10
            """, nativeQuery = true)
    List<Object[]> findBestsellersRaw();
}

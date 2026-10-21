package com.sunhom.order.repository;

import java.util.*;
import org.springframework.stereotype.Repository;

import com.sunhom.order.entity.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

    @Query(value = """
                SELECT pv.product_id
                FROM order_items oi
                JOIN product_variants pv ON pv.variant_id = oi.variant_id
                GROUP BY pv.product_id
                ORDER BY SUM(oi.quantity) DESC
                LIMIT :limit
            """, nativeQuery = true)
    List<UUID> findTopSellingProductIds(@Param("limit") int limit);
}

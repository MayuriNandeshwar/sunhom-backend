package com.sunhom.admin.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderStatusAnalyticsRepository
                extends JpaRepository<com.sunhom.order.entity.Orders, UUID> {

        @Query(value = """
                        SELECT status, COUNT(*) AS count
                        FROM orders
                        GROUP BY status
                        ORDER BY count DESC
                        """, nativeQuery = true)
        List<Object[]> fetchOrderStatusDistribution();
}

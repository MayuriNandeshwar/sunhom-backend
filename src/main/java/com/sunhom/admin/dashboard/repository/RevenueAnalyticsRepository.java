package com.sunhom.admin.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface RevenueAnalyticsRepository
    extends JpaRepository<com.sunhom.order.entity.Orders, UUID> {

  @Query(value = """
      SELECT
          DATE(o.created_at) AS date,
          COALESCE(SUM(o.total_amount), 0) AS revenue,
          COUNT(o.order_id) AS orders
      FROM orders o
      WHERE o.payment_status = 'PAID'
        AND o.created_at BETWEEN :start AND :end
      GROUP BY DATE(o.created_at)
      ORDER BY DATE(o.created_at)
      """, nativeQuery = true)
  List<Object[]> fetchRevenueAnalyticsRaw(
      OffsetDateTime start,
      OffsetDateTime end);
}

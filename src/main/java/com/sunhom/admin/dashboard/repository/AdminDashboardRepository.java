package com.sunhom.admin.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sunhom.order.entity.Orders;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Repository
public interface AdminDashboardRepository
                extends JpaRepository<Orders, UUID> {

        /*
         * ==========================
         * OVERALL METRICS
         * ==========================
         */

        @Query(value = "SELECT COUNT(*) FROM orders", nativeQuery = true)
        long totalOrders();

        @Query(value = """
                        SELECT COALESCE(SUM(total_amount), 0)
                        FROM orders
                        WHERE payment_status = 'PAID'
                        """, nativeQuery = true)
        BigDecimal totalRevenue();

        @Query(value = """
                        SELECT COUNT(*)
                        FROM users
                        WHERE role = 'CUSTOMER'
                        """, nativeQuery = true)
        long totalCustomers();

        @Query(value = """
                        SELECT COUNT(*)
                        FROM inventory
                        WHERE available_quantity < low_stock_threshold
                        """, nativeQuery = true)
        long lowStockItems();

        /*
         * ==========================
         * PERIOD BASED
         * ==========================
         */

        @Query(value = """
                        SELECT COUNT(*)
                        FROM orders
                        WHERE created_at BETWEEN :start AND :end
                        """, nativeQuery = true)
        long ordersBetween(OffsetDateTime start, OffsetDateTime end);

        @Query(value = """
                        SELECT COALESCE(SUM(total_amount), 0)
                        FROM orders
                        WHERE payment_status = 'PAID'
                          AND created_at BETWEEN :start AND :end
                        """, nativeQuery = true)
        BigDecimal revenueBetween(OffsetDateTime start, OffsetDateTime end);
}

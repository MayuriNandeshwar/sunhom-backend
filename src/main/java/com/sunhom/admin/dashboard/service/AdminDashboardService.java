package com.sunhom.admin.dashboard.service;

//import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sunhom.admin.dashboard.dto.DashboardSummaryDto;
import com.sunhom.admin.dashboard.model.DashboardPeriod;
import com.sunhom.admin.dashboard.repository.AdminDashboardRepository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
public class AdminDashboardService {

    private final AdminDashboardRepository repo;

    public AdminDashboardService(AdminDashboardRepository repo) {
        this.repo = repo;
    }

    /*
     * =============================
     * SUMMARY (CACHED)
     * =============================
     */

    // @Cacheable(value = "dashboard-summary", key = "#period")
    public DashboardSummaryDto getSummary(DashboardPeriod period) {

        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime start;
        OffsetDateTime end = now;

        switch (period) {
            case TODAY -> start = now.toLocalDate().atStartOfDay().atOffset(now.getOffset());
            case WEEK -> start = now.minusDays(7);
            case MONTH -> start = now.withDayOfMonth(1);
            default -> {
                return new DashboardSummaryDto(
                        repo.totalOrders(),
                        repo.totalRevenue(),
                        repo.totalCustomers(),
                        repo.lowStockItems());
            }
        }

        long orders = repo.ordersBetween(start, end);
        BigDecimal revenue = repo.revenueBetween(start, end);

        return new DashboardSummaryDto(
                orders,
                revenue,
                repo.totalCustomers(),
                repo.lowStockItems());
    }
}

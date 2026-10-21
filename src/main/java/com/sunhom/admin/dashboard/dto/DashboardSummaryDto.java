package com.sunhom.admin.dashboard.dto;

import java.math.BigDecimal;

public class DashboardSummaryDto {

    private long totalOrders;
    private BigDecimal totalRevenue;
    private long totalCustomers;
    private long lowStockItems;

    public DashboardSummaryDto(
            long totalOrders,
            BigDecimal totalRevenue,
            long totalCustomers,
            long lowStockItems) {
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
        this.totalCustomers = totalCustomers;
        this.lowStockItems = lowStockItems;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public long getTotalCustomers() {
        return totalCustomers;
    }

    public long getLowStockItems() {
        return lowStockItems;
    }
}

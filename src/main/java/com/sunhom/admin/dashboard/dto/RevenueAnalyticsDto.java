package com.sunhom.admin.dashboard.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RevenueAnalyticsDto {

    private LocalDate date;
    private BigDecimal revenue;
    private long orders;

    public RevenueAnalyticsDto(
            LocalDate date,
            BigDecimal revenue,
            long orders) {
        this.date = date;
        this.revenue = revenue;
        this.orders = orders;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public long getOrders() {
        return orders;
    }
}

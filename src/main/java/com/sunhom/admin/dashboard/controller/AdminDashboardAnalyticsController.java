package com.sunhom.admin.dashboard.controller;

import org.springframework.web.bind.annotation.*;

import com.sunhom.admin.dashboard.dto.RevenueAnalyticsDto;
import com.sunhom.admin.dashboard.service.RevenueAnalyticsService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/dashboard/analytics")
public class AdminDashboardAnalyticsController {

    private final RevenueAnalyticsService service;

    public AdminDashboardAnalyticsController(
            RevenueAnalyticsService service) {
        this.service = service;
    }

    @GetMapping("/revenue")
    public List<RevenueAnalyticsDto> getRevenueAnalytics(
            @RequestParam(defaultValue = "30") int days) {
        return service.getRevenueAnalytics(days);
    }
}

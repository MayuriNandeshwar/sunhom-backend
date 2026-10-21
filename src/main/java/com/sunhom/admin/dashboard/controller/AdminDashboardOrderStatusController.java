package com.sunhom.admin.dashboard.controller;

import org.springframework.web.bind.annotation.*;

import com.sunhom.admin.dashboard.dto.OrderStatusDistributionDto;
import com.sunhom.admin.dashboard.service.OrderStatusAnalyticsService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/dashboard/analytics")
public class AdminDashboardOrderStatusController {

    private final OrderStatusAnalyticsService service;

    public AdminDashboardOrderStatusController(
            OrderStatusAnalyticsService service) {
        this.service = service;
    }

    @GetMapping("/order-status")
    public List<OrderStatusDistributionDto> getOrderStatusDistribution() {
        return service.getOrderStatusDistribution();
    }
}

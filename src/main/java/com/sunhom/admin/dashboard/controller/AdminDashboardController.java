package com.sunhom.admin.dashboard.controller;

import org.springframework.web.bind.annotation.*;

import com.sunhom.admin.dashboard.dto.DashboardSummaryDto;
import com.sunhom.admin.dashboard.model.DashboardPeriod;
import com.sunhom.admin.dashboard.service.AdminDashboardService;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {

    private final AdminDashboardService service;

    public AdminDashboardController(AdminDashboardService service) {
        this.service = service;
    }

    @GetMapping("/summary")
    public DashboardSummaryDto getSummary(
            @RequestParam(defaultValue = "OVERALL") DashboardPeriod period) {
        return service.getSummary(period);
    }
}

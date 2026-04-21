package com.sunhom.admin.dashboard.service;

//import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sunhom.admin.dashboard.dto.OrderStatusDistributionDto;
import com.sunhom.admin.dashboard.repository.OrderStatusAnalyticsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderStatusAnalyticsService {

    private final OrderStatusAnalyticsRepository repository;

    public OrderStatusAnalyticsService(
            OrderStatusAnalyticsRepository repository) {
        this.repository = repository;
    }

    // @Cacheable(value = "admin-dashboard-order-status:v1")
    public List<OrderStatusDistributionDto> getOrderStatusDistribution() {

        List<Object[]> rows = repository.fetchOrderStatusDistribution();

        return rows.stream()
                .map(row -> new OrderStatusDistributionDto(
                        (String) row[0],
                        ((Number) row[1]).longValue()))
                .collect(Collectors.toList());
    }
}

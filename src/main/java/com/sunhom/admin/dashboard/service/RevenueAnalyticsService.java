package com.sunhom.admin.dashboard.service;

//import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sunhom.admin.dashboard.dto.RevenueAnalyticsDto;
import com.sunhom.admin.dashboard.repository.RevenueAnalyticsRepository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RevenueAnalyticsService {

    private final RevenueAnalyticsRepository repository;

    public RevenueAnalyticsService(RevenueAnalyticsRepository repository) {
        this.repository = repository;
    }

   // @Cacheable(value = "admin-dashboard-revenue-analytics:v1", key = "#days")
    public List<RevenueAnalyticsDto> getRevenueAnalytics(int days) {

        OffsetDateTime end = OffsetDateTime.now();
        OffsetDateTime start = end.minusDays(days);

        List<Object[]> rows = repository.fetchRevenueAnalyticsRaw(start, end);

        return rows.stream()
                .map(row -> new RevenueAnalyticsDto(
                        ((java.sql.Date) row[0]).toLocalDate(),
                        (BigDecimal) row[1],
                        ((Number) row[2]).longValue()))
                .collect(Collectors.toList());
    }
}

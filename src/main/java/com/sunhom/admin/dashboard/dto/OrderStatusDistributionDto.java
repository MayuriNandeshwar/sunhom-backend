package com.sunhom.admin.dashboard.dto;

public class OrderStatusDistributionDto {

    private String status;
    private long count;

    public OrderStatusDistributionDto(String status, long count) {
        this.status = status;
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public long getCount() {
        return count;
    }
}

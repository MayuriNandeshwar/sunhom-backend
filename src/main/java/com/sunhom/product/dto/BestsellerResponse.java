package com.sunhom.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Versioned API response for bestseller products
 * Version: v1.0
 * Contract frozen - do not change without version bump
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BestsellerResponse {

    /**
     * API version identifier
     */
    @JsonProperty("version")
    private String version = "v1.0";

    /**
     * Timestamp when data was fetched/cached
     */
    @JsonProperty("timestamp")
    private Long timestamp;

    /**
     * Total number of bestseller products returned
     */
    @JsonProperty("total")
    private Integer total;

    /**
     * List of bestseller products
     */
    @JsonProperty("data")
    private List<BestsellerProductDto> data;

    /**
     * Whether data is from cache
     */
    @JsonProperty("cached")
    private Boolean cached;
}
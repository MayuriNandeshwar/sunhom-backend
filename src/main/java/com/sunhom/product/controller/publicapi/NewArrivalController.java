package com.sunhom.product.controller.publicapi;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.sunhom.product.dto.publicapi.NewArrivalProductDto;
import com.sunhom.product.service.publicapi.NewArrivalService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public/products")
@RequiredArgsConstructor
public class NewArrivalController {

    private final NewArrivalService service;

    /**
     * Homepage endpoint
     * Default limit = 8
     */
    @GetMapping("/new-arrivals")
    public Map<String, Object> getNewArrivals(
            @RequestParam(defaultValue = "8") int limit) {

        List<NewArrivalProductDto> data = service.getNewArrivals(limit);

        Map<String, Object> response = new HashMap<>();
        response.put("version", "v1.0");
        response.put("timestamp", System.currentTimeMillis());
        response.put("total", data.size());
        response.put("data", data);

        return response;
    }

    /**
     * Admin cache clear
     */
    @PostMapping("/new-arrivals/refresh")
    public void refreshCache() {
        service.evictNewArrivalsCache();
    }
}
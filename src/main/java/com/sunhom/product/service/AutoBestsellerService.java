package com.sunhom.product.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunhom.order.repository.OrderItemRepository;
import com.sunhom.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutoBestsellerService {

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void refreshAutoBestsellers(int limit) {

        // 1️⃣ Clear previous auto bestsellers
        productRepository.clearAutoBestsellers();

        // 2️⃣ Find top selling product IDs
        List<UUID> productIds = orderItemRepository.findTopSellingProductIds(limit);

        if (productIds.isEmpty()) {
            return;
        }

        // 3️⃣ Mark new ones
        productRepository.markAsAutoBestseller(productIds);
    }
}

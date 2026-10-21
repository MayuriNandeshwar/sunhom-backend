package com.sunhom.product.controller.admin;

import java.util.UUID;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sunhom.product.service.admin.AdminProductImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/products/{productId}/images")
@RequiredArgsConstructor
public class AdminProductImageController {

    private final AdminProductImageService imageService;

    @PostMapping
    public void upload(
            @PathVariable UUID productId,
            @RequestParam MultipartFile file) {

        imageService.upload(productId, file);
    }

    @PutMapping("/reorder")
    public void reorder(
            @PathVariable UUID productId,
            @RequestBody List<UUID> orderedImageIds) {

        imageService.reorder(productId, orderedImageIds);
    }

    // @PutMapping("/{productId}/images/reorder")
    // public void reorder(
    // @PathVariable UUID productId,
    // @RequestBody List<UUID> imageIds) {

    // imageService.reorder(productId, imageIds);
    // }

    @DeleteMapping("/{imageId}")
    public void delete(
            @PathVariable UUID productId,
            @PathVariable UUID imageId) {

        imageService.delete(productId, imageId);
    }

    // @DeleteMapping("/{productId}/images/{imageId}")
    // public void delete(
    // @PathVariable UUID productId,
    // @PathVariable UUID imageId) {

    // imageService.delete(productId, imageId);
    // }

}

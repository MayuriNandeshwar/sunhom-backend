package com.sunhom.product.service.admin;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunhom.product.dto.admin.*;
import com.sunhom.product.entity.*;
import com.sunhom.product.repository.*;

import java.time.OffsetDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminProductEditService {

    private final ProductRepository productRepo;
    private final ProductVariantRepository variantRepo;
    private final ProductEntityAttributeRepository entityAttributeRepo;
    private final ProductImageRepository imageRepo;
    private final ProductAttributeRepository attributeMasterRepo;

    @SuppressWarnings("null")
    @Transactional(readOnly = true)

    public AdminProductEditDto getProductForEdit(UUID productId) {

        Products p = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        AdminProductEditDto dto = new AdminProductEditDto();
        dto.setProductId(p.getProductId());
        dto.setProductName(p.getProductName());
        dto.setSlug(p.getSlug());
        dto.setSkuBase(p.getSkuBase());
        dto.setShortDescription(p.getShortDescription());
        dto.setProductDescription(p.getProductDescription());
        dto.setBrand(p.getBrand());
        dto.setSearchKeywords(p.getSearchKeywords());
        dto.setActive(p.isActive());
        dto.setFeatured(p.isFeatured());
        dto.setManualBestseller(p.isManualBestseller());
        dto.setSeoTitle(p.getSeoTitle());
        dto.setSeoDescription(p.getSeoDescription());
        dto.setSeoKeywords(p.getSeoKeywords());
        dto.setCategoryId(p.getCategories().getCategoryId());
        dto.setProductTypeId(p.getProductType().getProductTypeId());

        dto.setVariants(variantRepo.findByProducts_ProductId(productId)
                .stream().map(this::mapVariant).toList());

        dto.setAttributes(entityAttributeRepo.findByProducts_ProductId(productId)
                .stream().map(this::mapAttribute).toList());

        dto.setImages(imageRepo.findByProducts_ProductIdOrderByPositionAsc(productId)
                .stream().map(this::mapImage).toList());

        return dto;
    }

    @SuppressWarnings("null")
    // @CacheEvict(value = "publicProduct", key = "#p.slug")
    @Transactional
    public void updateProduct(UUID productId, AdminProductUpdateDto dto) {

        Products p = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // -------------------------
        // BASIC FIELDS
        // -------------------------
        p.setProductName(dto.getProductName());
        p.setShortDescription(dto.getShortDescription());
        p.setProductDescription(dto.getProductDescription());
        p.setBrand(dto.getBrand());
        p.setSearchKeywords(dto.getSearchKeywords());
        p.setActive(dto.isActive());
        p.setFeatured(dto.isFeatured());
        p.setManualBestseller(dto.isManualBestseller());
        p.setSeoTitle(dto.getSeoTitle());
        p.setSeoDescription(dto.getSeoDescription());
        p.setSeoKeywords(dto.getSeoKeywords());
        p.setUpdatedAt(OffsetDateTime.now());

        productRepo.save(p);

        // -------------------------
        // UPDATE DEFAULT VARIANT PRICE
        // -------------------------
        variantRepo.findByProducts_ProductId(productId)
                .stream()
                .filter(ProductVariants::isDefault)
                .findFirst()
                .ifPresent(v -> {
                    v.setPrice(dto.getDefaultPrice());
                    v.setMrp(dto.getDefaultMrp());
                    v.setUpdatedAt(OffsetDateTime.now());
                    variantRepo.save(v);
                });

        // -------------------------
        // UPDATE ATTRIBUTES
        // -------------------------
        entityAttributeRepo.deleteByProducts_ProductId(productId);

        for (AdminProductAttributeDto a : dto.getAttributes()) {

            ProductEntityAttribute pea = ProductEntityAttribute.builder()
                    .products(p)
                    .attribute(attributeMasterRepo.getReferenceById(a.getAttributeId()))
                    .valueText(a.getValueText())
                    .valueNumber(a.getValueNumber())
                    .valueBoolean(a.getValueBoolean())
                    .createdAt(OffsetDateTime.now())
                    .build();

            entityAttributeRepo.save(pea);
        }

        // -------------------------
        // DO NOT TOUCH IMAGES HERE
        // -------------------------
    }

    private AdminVariantDto mapVariant(ProductVariants v) {
        AdminVariantDto d = new AdminVariantDto();
        d.setVariantId(v.getVariantId());
        d.setVariantTitle(v.getVariantTitle());
        d.setFragrance(v.getFragrance());
        d.setWeightGrams(v.getWeightGrams());
        d.setSizeLabel(v.getSizeLabel());
        d.setDurationHours(v.getDurationHours());
        d.setPrice(v.getPrice());
        d.setMrp(v.getMrp());
        d.setActive(v.isActive());
        d.setDefault(v.isDefault());
        return d;
    }

    private AdminProductAttributeDto mapAttribute(ProductEntityAttribute a) {
        AdminProductAttributeDto d = new AdminProductAttributeDto();
        d.setAttributeId(a.getAttribute().getAttributeId());
        d.setValueText(a.getValueText());
        d.setValueNumber(a.getValueNumber());
        d.setValueBoolean(a.getValueBoolean());
        return d;
    }

    private AdminProductImageDto mapImage(ProductImages i) {
        AdminProductImageDto d = new AdminProductImageDto();
        d.setProductImageId(i.getProductImageId());
        d.setImageUrl(i.getProductImageUrl());
        d.setAltText(i.getAltText());
        d.setPosition(i.getPosition());
        d.setPrimary(i.isPrimary());
        return d;
    }
}

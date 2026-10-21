package com.sunhom.product.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sunhom.product.dto.ProductAttributeAssignDto;
import com.sunhom.product.entity.ProductAttribute;
import com.sunhom.product.entity.ProductAttributeValue;
import com.sunhom.product.entity.ProductEntityAttribute;
import com.sunhom.product.entity.Products;
import com.sunhom.product.repository.ProductAttributeRepository;
import com.sunhom.product.repository.ProductAttributeValueRepository;
import com.sunhom.product.repository.ProductEntityAttributeRepository;
import com.sunhom.product.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductEntityAttributeService {

        private final ProductAttributeRepository attributeRepo;
        private final ProductAttributeValueRepository valueRepo;
        private final ProductEntityAttributeRepository entityAttrRepo;
        private final ProductRepository productRepo;

        @Transactional
        public void assignToProduct(UUID productId,
                        List<ProductAttributeAssignDto> dtos) {

                Products products = productRepo.findById(productId)
                                .orElseThrow(() -> new RuntimeException("Product not found"));

                entityAttrRepo.deleteByProducts_ProductId(productId);

                for (ProductAttributeAssignDto dto : dtos) {

                        ProductAttribute attr = attributeRepo
                                        .findByCode(dto.getAttributeCode())
                                        .orElseThrow(() -> new RuntimeException("Invalid attribute"));

                        ProductEntityAttribute pea = new ProductEntityAttribute();
                        pea.setProducts(products);
                        pea.setAttribute(attr);

                        switch (attr.getDataType()) {
                                case "TEXT" -> pea.setValueText(dto.getValueText());
                                case "NUMBER" -> pea.setValueNumber(
                                                dto.getValueNumber() == null ? null
                                                                : BigDecimal.valueOf(dto.getValueNumber()));
                                case "BOOLEAN" -> pea.setValueBoolean(dto.getValueBoolean());
                                case "ENUM" -> {
                                        ProductAttributeValue val = valueRepo
                                                        .findByAttribute_AttributeIdAndIsActiveTrueOrderByDisplayOrderAsc(
                                                                        attr.getAttributeId())
                                                        .stream()
                                                        .filter(v -> v.getValue().equals(dto.getValueEnum()))
                                                        .findFirst()
                                                        .orElseThrow(() -> new RuntimeException("Invalid enum value"));
                                        pea.setEnumValue(val);
                                }
                                default -> throw new RuntimeException("Unsupported data type");
                        }

                        entityAttrRepo.save(pea);
                }
        }
}

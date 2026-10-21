package com.sunhom.product.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.sunhom.product.dto.admin.*;
import com.sunhom.product.service.admin.AdminCategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService service;

    @GetMapping
    public List<CategoryResponseDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public CategoryResponseDto getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    public UUID create(@RequestBody @Valid CategoryCreateDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable UUID id,
            @RequestBody @Valid CategoryUpdateDto dto) {

        service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}

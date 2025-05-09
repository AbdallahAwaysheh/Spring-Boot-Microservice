package com.orange.e_shop.product_service.controller.admin;

import com.orange.e_shop.product_service.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
@SecurityRequirement(name = "auth")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@RequestParam Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody String name) {
        return categoryService.createCategory(name);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody Long id, @RequestBody String name) {
        return categoryService.updateCategory(id, name);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@RequestParam Long id) {
        return categoryService.deleteCategory(id);
    }

}

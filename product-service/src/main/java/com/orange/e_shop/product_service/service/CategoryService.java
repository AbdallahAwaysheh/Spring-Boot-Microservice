package com.orange.e_shop.product_service.service;

import com.orange.e_shop.product_service.model.Category;
import com.orange.e_shop.product_service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ResponseEntity<?> createCategory(String name) {
        Category category = Category.builder().name(name).build();
        categoryRepository.save(category);
        return ResponseEntity.status(201).build();
    }

    public ResponseEntity<?> deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.status(204).build();
    }

    public ResponseEntity<?> updateCategory(Long id, String name) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(name);
        categoryRepository.save(category);
        return ResponseEntity.status(200).build();
    }

    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    public ResponseEntity<?> getCategoryById(Long id) {
        return ResponseEntity.ok(categoryRepository.findById(id));
    }



}

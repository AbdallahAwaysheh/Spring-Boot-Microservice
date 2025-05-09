package com.orange.e_shop.product_service.controller;


import com.orange.e_shop.product_service.dto.ProductDto;
import com.orange.e_shop.product_service.dto.ProductResponse;
import com.orange.e_shop.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/{id}")
    public ProductResponse read(@RequestParam Long id) {
        return productService.getProductById(id);
    }

    @GetMapping
    public List<ProductResponse> readAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return productService.getAllProducts(page, size);
    }

    @GetMapping("/category/{id}")
    public List<ProductResponse> readByCategory(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        return productService.getAllProductsByCategory(id, page, size);
    }

}

package com.orange.e_shop.product_service.controller.admin;

import com.orange.e_shop.product_service.dto.ProductDto;
import com.orange.e_shop.product_service.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/product")
@RequiredArgsConstructor
@SecurityRequirement(name = "auth")
public class AdminProductController {
    private final ProductService productService;

    @PostMapping
    public ProductDto create(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @PutMapping("/{id}")
    public ProductDto update(
            @PathVariable Long id,
            @RequestBody ProductDto productDto) {
        return productService.updateProduct(id,productDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
    }


}

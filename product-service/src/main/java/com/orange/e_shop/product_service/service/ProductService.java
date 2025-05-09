package com.orange.e_shop.product_service.service;


import com.orange.e_shop.product_service.dto.ProductDto;
import com.orange.e_shop.product_service.dto.ProductResponse;
import com.orange.e_shop.product_service.model.Category;
import com.orange.e_shop.product_service.model.Product;
import com.orange.e_shop.product_service.repository.CategoryRepository;
import com.orange.e_shop.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public ProductDto createProduct(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .category(category)
                .tags(productDto.getTags())
                .build();
        productRepository.save(product);
        return productDto;
    }

    public List<ProductResponse> getAllProducts(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Product> products=productRepository.findAll(pageable);

        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .tags(product.getTags()).build();
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (product.isDeleted()) {
            return null;
        }
        return mapToProductResponse(product);
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (product.isDeleted()) {
            return null;
        }
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setTags(productDto.getTags());
        productRepository.save(product);
        return productDto;
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (product.isDeleted()) {
            return;
        }
        product.setDeleted(true);
        productRepository.save(product);
    }

    public List<ProductResponse> getAllProductsByCategory(Long id,int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Product> products= productRepository.findByCategoryIdAndDeleted(id,false,pageable);
        return products
                .stream()
                .map(this::mapToProductResponse)
                .toList();
    }
}

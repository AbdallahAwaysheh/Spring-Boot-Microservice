package com.orange.e_shop.product_service.service.tags;

import com.orange.e_shop.product_service.model.Product;
import com.orange.e_shop.product_service.model.Tag;
import com.orange.e_shop.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductTaggingService {
    private final ProductRepository productRepository;
    private final TagService tagService;

    public void tagProduct(Long productId, Long tagId) {
        Tag tag = tagService.getTagById(tagId);
        Product product = productRepository.findById(productId).orElse(null);
        product.getTags().add(tag);
        productRepository.save(product);
    }

    public void untagProduct(Long productId, Long tagId) {
        Product product = productRepository.findById(productId).orElse(null);
        Tag tag = tagService.getTagById(tagId);
        product.getTags().remove(tag);
        productRepository.save(product);
    }
}

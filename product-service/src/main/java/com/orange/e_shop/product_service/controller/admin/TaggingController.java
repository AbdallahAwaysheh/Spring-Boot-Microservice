package com.orange.e_shop.product_service.controller.admin;

import com.orange.e_shop.product_service.model.Tag;
import com.orange.e_shop.product_service.service.tags.ProductTaggingService;
import com.orange.e_shop.product_service.service.tags.TagService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/tagging")
@RequiredArgsConstructor
@SecurityRequirement(name = "auth")
public class TaggingController {

    private final ProductTaggingService productTaggingService;
    private final TagService tagService;

    @GetMapping
    public List<Tag> getTags() {
        return tagService.getTags();
    }

    @PutMapping("/tag")
    public void tagProduct(@RequestParam Long productId, @RequestParam Long tagId) {
        productTaggingService.tagProduct(productId, tagId);
    }

    @PutMapping("/untag")
    public void untagProduct(@RequestParam Long productId, @RequestParam Long tagId) {
        productTaggingService.untagProduct(productId, tagId);
    }

    @PostMapping
    public void addTag(@RequestParam String tagName) {
        tagService.addTag(tagName);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }
}


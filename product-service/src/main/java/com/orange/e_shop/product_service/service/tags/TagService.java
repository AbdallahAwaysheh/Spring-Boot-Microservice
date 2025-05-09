package com.orange.e_shop.product_service.service.tags;

import com.orange.e_shop.product_service.model.Tag;
import com.orange.e_shop.product_service.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public void addTag(String tagName) {
        Tag tag = Tag.builder().name(tagName).build();
        tagRepository.save(tag);
    }

    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    public Tag getTagById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

}

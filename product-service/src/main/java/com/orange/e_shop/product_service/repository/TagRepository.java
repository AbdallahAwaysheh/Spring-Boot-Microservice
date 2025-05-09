package com.orange.e_shop.product_service.repository;

import com.orange.e_shop.product_service.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}

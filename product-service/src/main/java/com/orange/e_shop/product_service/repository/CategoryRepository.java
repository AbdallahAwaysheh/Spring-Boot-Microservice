package com.orange.e_shop.product_service.repository;


import com.orange.e_shop.product_service.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}

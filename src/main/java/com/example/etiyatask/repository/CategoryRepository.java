package com.example.etiyatask.repository;

import com.example.etiyatask.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Modifying
    @Query(value = "delete from products_categories where categories_category_id = :categoryId", nativeQuery = true)
    void deleteCategoryFromAdditionTable(Long categoryId);
}

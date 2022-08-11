package com.example.etiyatask.service;

import com.example.etiyatask.DTO.CategoryDTO;
import com.example.etiyatask.model.Category;
import com.example.etiyatask.model.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface CategoryService {
    Category getCategory(Long categoryId);

    List<Product> getProductsList(Long categoryId);

    double getSumOfCategory(Long categoryId);

    List<Category> findAll(Pageable pageable);

    void saveCategory(CategoryDTO categoryDTO);

    void deleteCategory(Long categoryId);

    void updateCategory(Long categoryId, CategoryDTO categoryDTO);
}

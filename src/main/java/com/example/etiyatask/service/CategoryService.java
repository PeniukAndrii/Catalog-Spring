package com.example.etiyatask.service;

import com.example.etiyatask.DTO.CategoryDTO;
import com.example.etiyatask.model.Category;
import com.example.etiyatask.model.Product;
import com.example.etiyatask.repository.CategoryRepository;
import com.example.etiyatask.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.etiyatask.constants.ErrorMessages.Category.CATEGORY_NOT_FOUND_ERROR;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(CATEGORY_NOT_FOUND_ERROR, categoryId)));
    }

    public List<Product> getProductsList(Long categoryId, Pageable pageable) {
        productRepository.findAll(pageable);
        return getCategory(categoryId).getProducts();
    }

    public double getSumOfCategory(Long categoryId) {
        return getCategory(categoryId).getProducts().stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public List<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .collect(Collectors.toList());
    }

    public void saveCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteCategoryFromAdditionTable(categoryId);
        categoryRepository.deleteById(categoryId);
    }

    public void updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category category = getCategory(categoryId);
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
    }

}

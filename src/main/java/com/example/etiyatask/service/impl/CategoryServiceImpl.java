package com.example.etiyatask.service.impl;

import com.example.etiyatask.DTO.CategoryDTO;
import com.example.etiyatask.model.Category;
import com.example.etiyatask.model.Product;
import com.example.etiyatask.repository.CategoryRepository;
import com.example.etiyatask.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(CATEGORY_NOT_FOUND_ERROR, categoryId)));
    }

    @Override
    public List<Product> getProductsList(Long categoryId) {
        return categoryRepository.getReferenceById(categoryId).getProducts();
    }

    @Override
    public double getSumOfCategory(Long categoryId) {
        return getCategory(categoryId).getProducts().stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    @Override
    public List<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .collect(Collectors.toList());
    }

    @Override
    public void saveCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteCategoryFromAdditionTable(categoryId);
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public void updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category category = getCategory(categoryId);
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
    }

}

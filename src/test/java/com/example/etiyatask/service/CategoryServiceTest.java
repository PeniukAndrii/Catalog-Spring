package com.example.etiyatask.service;

import com.example.etiyatask.DTO.CategoryDTO;
import com.example.etiyatask.model.Category;
import com.example.etiyatask.model.Product;
import com.example.etiyatask.repository.CategoryRepository;
import com.example.etiyatask.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CategoryServiceImpl.class)
public class CategoryServiceTest {

    private static final Long FAKE_CATEGORY_ID = 1L;

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;


    @Test
    void saveProduct() {
        //Given
        CategoryDTO categoryDTO = new CategoryDTO();

        //When
        categoryService.saveCategory(categoryDTO);

        //Then
        verify(categoryRepository)
                .save(any(Category.class));
    }

    @Test
    void getCategory() {
        //Given
        Category category = new Category();

        when(categoryRepository.findById(any()))
                .thenReturn(Optional.of(category));

        //When
        categoryService.getCategory(FAKE_CATEGORY_ID);

        //Then
        verify(categoryRepository)
                .findById(FAKE_CATEGORY_ID);
    }

    @Test
    void getCategoryFail() {
        when(categoryRepository.findById(any()))
                .thenThrow(new EntityNotFoundException());

        assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.getCategory(FAKE_CATEGORY_ID)
        );
    }

    @Test
    void getSumOfCategory() {
        //Given
        CategoryDTO categoryDTO = new CategoryDTO();
        Category category = new Category();

        when(categoryRepository.findById(FAKE_CATEGORY_ID)).thenReturn(Optional.of(category));

        //When
        categoryService.updateCategory(FAKE_CATEGORY_ID, categoryDTO);

        //Then
        verify(categoryRepository).save(category);
    }

    @Test
    void getProductsList() {
        //Given
        Category category = new Category();
        Product product = new Product();
        category.setProducts(List.of(product));

        when(categoryRepository.getReferenceById(any()))
                .thenReturn(category);

        //When
        categoryService.getProductsList(FAKE_CATEGORY_ID);

        //Then
        verify(categoryRepository)
                .getReferenceById(FAKE_CATEGORY_ID);
    }

    @Test
    void updateCategory() {
        //Given
        Product product = new Product();
        Product product2 = new Product();
        product.setPrice(100);
        product2.setPrice(100);
        Category category = new Category();
        category.setProducts(Arrays.asList(product, product2));

        when(categoryRepository.findById(FAKE_CATEGORY_ID)).thenReturn(Optional.of(category));

        //When
        double sumOfCategory = categoryService.getSumOfCategory(FAKE_CATEGORY_ID);

        //Then
        assertEquals(200, sumOfCategory);
    }

    @Test
    void deleteCategory() {
        categoryService.deleteCategory(FAKE_CATEGORY_ID);

        verify(categoryRepository).deleteCategoryFromAdditionTable(FAKE_CATEGORY_ID);
        verify(categoryRepository).deleteById(FAKE_CATEGORY_ID);
    }


}

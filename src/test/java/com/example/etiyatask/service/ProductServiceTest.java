package com.example.etiyatask.service;

import com.example.etiyatask.DTO.ProductDTO;
import com.example.etiyatask.model.Category;
import com.example.etiyatask.model.Product;
import com.example.etiyatask.repository.ProductRepository;
import com.example.etiyatask.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ProductServiceImpl.class)
public class ProductServiceTest {

    private static final Long FAKE_PRODUCT_ID = 1L;
    private static final Long FAKE_CATEGORY_ID = 1L;
    private static final String FAKE_LABEL = "label";

    @Autowired
    private ProductService productService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private ProductRepository productRepository;


    @Test
    void saveProduct() {
        //Given
        Product product = new Product();

        //When
        productService.saveProduct(product);

        //Then
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void saveProductWithCategories() {
        //Given
        Product product = new Product();

        //When
        productService.saveProductWithCategories(product, List.of(FAKE_PRODUCT_ID));

        //Then
        verify(productRepository).save(product);
    }

    @Test
    void getProductFail() {
        when(productRepository.findById(any()))
                .thenThrow(new EntityNotFoundException());

        assertThrows(
                EntityNotFoundException.class,
                () -> productService.getProduct(FAKE_PRODUCT_ID)
        );
    }

    @Test
    void findByLabelInCategory() {
        //Given
        Category category = new Category();

        when(categoryService.getCategory(FAKE_CATEGORY_ID)).thenReturn(category);

        //When
        productService.findByLabelInCategory(FAKE_LABEL, FAKE_CATEGORY_ID);

        //Then
        verify(categoryService).getCategory(FAKE_CATEGORY_ID);
    }


    @Test
    void updateProductCategories() {
        //Given
        Product product = new Product();

        when(productRepository.findById(FAKE_PRODUCT_ID)).thenReturn(Optional.of(product));

        //When
        productService.updateProductCategories(FAKE_PRODUCT_ID, List.of(FAKE_CATEGORY_ID));

        //Then
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct() {
        //Given
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();

        when(productRepository.findById(FAKE_PRODUCT_ID))
                .thenReturn(Optional.of(product));

        //When
        productService.updateProduct(FAKE_PRODUCT_ID, productDTO);

        //Then
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void deleteProduct() {
        productService.deleteProduct(FAKE_PRODUCT_ID);

        verify(productRepository).deleteById(FAKE_PRODUCT_ID);
    }
}

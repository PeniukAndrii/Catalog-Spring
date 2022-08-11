package com.example.etiyatask.service;

import com.example.etiyatask.DTO.ProductDTO;
import com.example.etiyatask.model.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProductService {
    void saveProduct(Product product);

    void saveProductWithCategories(Product product, List<Long> categoriesIds);

    Product getProduct(Long productId);

    List<Product> findAll(Pageable pageable);

    List<Product> findByLabelInCategory(String label, Long categoryId);

    List<Product> search(String label);

    void updateProductCategories(Long productId, List<Long> newCategories);

    void updateProduct(Long productId, ProductDTO productDTO);

    void deleteProduct(Long productId);
}

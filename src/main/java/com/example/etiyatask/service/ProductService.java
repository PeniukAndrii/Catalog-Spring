package com.example.etiyatask.service;

import com.example.etiyatask.DTO.ProductDTO;
import com.example.etiyatask.model.Category;
import com.example.etiyatask.model.Product;
import com.example.etiyatask.repository.CategoryRepository;
import com.example.etiyatask.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.etiyatask.constants.ErrorMessages.Category.CATEGORY_NOT_FOUND_ERROR;
import static com.example.etiyatask.constants.ErrorMessages.Product.PRODUCT_NOT_FOUND_ERROR;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(PRODUCT_NOT_FOUND_ERROR, productId)));
    }

    public List<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).stream()
                .collect(Collectors.toList());
    }

    public List<Product> search(String label) {
        return productRepository.findAllByLabelContains(label);
    }

    public List<Product> findByLabelInCategory(String label, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(CATEGORY_NOT_FOUND_ERROR, categoryId)));
        return category.getProducts().stream()
                .filter(p -> p.getLabel().contains(label))
                .collect(Collectors.toList());
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void saveProductWithCategories(Product product, List<Long> categoriesIds) {
        product.setCategories(getCategoriesByIds(categoriesIds));
        saveProduct(product);
    }

    public void updateProductCategories(Long productId, List<Long> newCategories) {
        Product product = getProduct(productId);
        product.setCategories(getCategoriesByIds(newCategories));

        saveProduct(product);
    }

    public void updateProduct(Long productId, ProductDTO productDTO) {
        Product product = getProduct(productId);
        product.setPrice(productDTO.getPrice());
        product.setLabel(productDTO.getLabel());
        product.setRating(productDTO.getRating());
        product.setRating(productDTO.getRating());
        saveProduct(product);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    private Set<Category> getCategoriesByIds(List<Long> categoriesIds) {
        return categoriesIds.stream()
                .map(categoryId -> categoryRepository.getReferenceById(categoryId))
                .collect(Collectors.toSet());
    }
}

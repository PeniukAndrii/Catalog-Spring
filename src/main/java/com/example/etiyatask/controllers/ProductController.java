package com.example.etiyatask.controllers;

import com.example.etiyatask.DTO.ProductDTO;
import com.example.etiyatask.model.Product;
import com.example.etiyatask.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/product")
@Api(value = "Product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @ApiOperation(value = "Save product")
    public void save(@RequestBody Product product) {
        productService.saveProduct(product);
    }

    @PostMapping("/save/category")
    @ApiOperation(value = "Save product in some categories")
    public void saveWithCategories(@RequestBody Product product,
                                   @RequestParam List<Long> categoriesIds) {
        productService.saveProductWithCategories(product, categoriesIds);
    }

    @GetMapping("/{productId}")
    @ApiOperation(value = "Get product")
    public Product getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    @GetMapping("/findAll")
    @ApiOperation(value = "Find all", response = Product.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.", defaultValue = "5"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). ")
    })
    public List<Product> findAll(@ApiIgnore Pageable pageable) {
        return productService.findAll(pageable);
    }

    @GetMapping("/search")
    @ApiOperation(value = "Find product by label")
    public List<Product> searchAllProduct(@RequestParam String label) {
        return productService.search(label);
    }

    @GetMapping("/search/{categoryId}")
    @ApiOperation(value = "Find product by label in specific category")
    public List<Product> searchAllProduct(@RequestParam String label,
                                          @PathVariable Long categoryId) {
        return productService.findByLabelInCategory(label, categoryId);
    }

    @PutMapping("/update/categories")
    @ApiOperation(value = "Update product categories")
    public void updateCategories(@RequestParam List<Long> categoriesIds,
                                 @RequestParam Long productId) {
        productService.updateProductCategories(productId, categoriesIds);
    }

    @PutMapping("/{productId}")
    @ApiOperation(value = "Update product")
    public void update(@PathVariable Long productId,
                       @RequestBody ProductDTO productDTO) {
        productService.updateProduct(productId, productDTO);
    }

    @DeleteMapping("/{productId}")
    @ApiOperation(value = "Delete product")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}

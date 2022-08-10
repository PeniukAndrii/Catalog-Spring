package com.example.etiyatask.controllers;

import com.example.etiyatask.DTO.CategoryDTO;
import com.example.etiyatask.model.Category;
import com.example.etiyatask.model.Product;
import com.example.etiyatask.service.CategoryService;
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
@RequestMapping("/category")
@Api(value = "Category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @ApiOperation(value = "Save category")
    public void saveCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.saveCategory(categoryDTO);
    }

    @GetMapping("/{categoryId}")
    @ApiOperation(value = "Get category")
    public Category getCategory(@PathVariable Long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @GetMapping("/get/sum")
    @ApiOperation(value = "Get sum of category")
    public double getSumOfCategory(@RequestParam Long categoryId) {
        return categoryService.getSumOfCategory(categoryId);
    }

    @GetMapping("/get/{categoryId}")
    @ApiOperation(value = "Get products inside category")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.", defaultValue = "5"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). ")
    })
    public List<Product> getProductsOfCategory(@PathVariable Long categoryId, @ApiIgnore Pageable pageable) {
        return categoryService.getProductsList(categoryId, pageable);
    }

    @GetMapping("/findAll")
    @ApiOperation(value = "Find all", response = Category.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.", defaultValue = "5"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). ")
    })
    public List<Category> findAll(@ApiIgnore Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @PutMapping("/{categoryId}")
    @ApiOperation(value = "Update category")
    public void update(@PathVariable Long categoryId,
                       @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(categoryId, categoryDTO);
    }

    @DeleteMapping("/{categoryId}")
    @ApiOperation(value = "Delete category")
    public void deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }

}

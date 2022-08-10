package com.example.etiyatask.DTO;

import com.example.etiyatask.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ProductCategoriesDTO extends ProductDTO{

    private Set<Category> categories = new HashSet<>();

}

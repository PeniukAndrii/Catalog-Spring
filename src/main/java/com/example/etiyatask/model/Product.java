package com.example.etiyatask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static com.example.etiyatask.constants.Constants.Product.PRODUCT_MAX_RATING;
import static com.example.etiyatask.constants.Constants.Product.PRODUCT_MIN_RATING;
import static com.example.etiyatask.constants.ErrorMessages.Product.PRODUCT_RATING_VALUE_OUT_OF_RANGE;

@Data
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @Column(nullable = false, name = "PRODUCT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "LABEL")
    private String label;

    @Column(name = "PRICE")
    private double price;

    @Column(name = "RATING")
    @Range(min = PRODUCT_MIN_RATING, max = PRODUCT_MAX_RATING, message = PRODUCT_RATING_VALUE_OUT_OF_RANGE)
    private int rating;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PRODUCTS_CATEGORIES")
    @JsonIgnore
    private Set<Category> categories = new HashSet<>();

}

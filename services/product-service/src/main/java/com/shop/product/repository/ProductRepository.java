package com.shop.product.repository;

import com.shop.product.document.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByCategory(String category);

    List<Product> findByPriceLessThan(BigDecimal price);

    List<Product> findByNameContainingIgnoreCase(String text);

}

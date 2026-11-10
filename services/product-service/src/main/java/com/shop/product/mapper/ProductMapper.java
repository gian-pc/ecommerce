package com.shop.product.mapper;

import com.shop.product.document.Product;
import com.shop.product.dto.ProductRequest;
import com.shop.product.dto.ProductResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ProductMapper {

    public Product toDocument(ProductRequest request) {

        return Product.builder()
                .name(request.name())
                .description(request.description())
                .category(request.category())
                .price(request.price())
                .attributes(request.attributes())
                .createdAt(Instant.now())
                .build();

    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getPrice(),
                product.getAttributes(),
                product.getCreatedAt()
        );
    }

    public void updateDocument(Product product, ProductRequest request){

        product.setName(request.name());
        product.setDescription(request.description());
        product.setCategory(request.category());
        product.setPrice(request.price());
        product.setAttributes(request.attributes());

    }
}


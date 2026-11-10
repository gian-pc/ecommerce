package com.shop.product.service;

import com.shop.product.document.Product;
import com.shop.product.dto.ProductRequest;
import com.shop.product.dto.ProductResponse;
import com.shop.product.exception.ProductNotFoundException;
import com.shop.product.mapper.ProductMapper;
import com.shop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j                  // Lombok: crea la variable "log" para escribir en consola
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponse create(ProductRequest request) {
        Product product = productMapper.toDocument(request);
        Product saved = productRepository.save(product);
        return productMapper.toResponse(saved);
    }

    @Cacheable(value = "product", key = "#id") // busca primero en Redis
    public ProductResponse findById(String id){
        log.info("Buscando en MongoDB el producto {}", id); // solo se imprime si va a Mongo
        Product product = productRepository.findById(id)
                .orElseThrow( () -> new ProductNotFoundException(id));
        return productMapper.toResponse(product);
    }

    @CacheEvict(value = "product", key = "#id")
    public ProductResponse update(String id, ProductRequest request){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productMapper.updateDocument(product, request);
        Product saved = productRepository.save(product);
        return productMapper.toResponse(saved);
    }

    @CacheEvict(value = "product", key = "#id")              // borra product::id de Redis
    public void delete(String id) {                          // void: no devuelve nada
        if (!productRepository.existsById(id)) {             // 1. ¿existe? (método gratis de MongoRepository)
            throw new ProductNotFoundException(id);          //    si no → 404
        }
        productRepository.deleteById(id);                    // 2. lo borra de Mongo
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()        // trae TODOS los Product de Mongo → List<Product>
                .stream()                         // los pone en una "fila" para procesarlos uno por uno
                .map(productMapper::toResponse)   // convierte cada Product → ProductResponse
                .toList();                        // junta el resultado en una List<ProductResponse>
    }
}

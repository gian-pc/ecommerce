package com.shop.product.controller;

import com.shop.product.dto.ProductRequest;
import com.shop.product.dto.ProductResponse;
import com.shop.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody ProductRequest request){
        return productService.create(request);
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable String id){
        return productService.findById(id);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable String id, @Valid @RequestBody ProductRequest request){
        return productService.update(id, request);
    }

    @DeleteMapping("/{id}")                        // atiende DELETE /products/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)         // responde 204
    public void delete(@PathVariable String id) {  // void: no hay nada que devolver
        productService.delete(id);
    }

    @GetMapping                                  // atiende GET /products (sin id)
    public List<ProductResponse> findAll() {     // devuelve una lista → Jackson la convierte en un array JSON
        return productService.findAll();
    }

}

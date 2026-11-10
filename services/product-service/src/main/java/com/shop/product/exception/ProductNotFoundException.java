package com.shop.product.exception;

public class ProductNotFoundException  extends RuntimeException {

    public ProductNotFoundException( String id){
        super("Producto no encontrado: " + id);
    }
}

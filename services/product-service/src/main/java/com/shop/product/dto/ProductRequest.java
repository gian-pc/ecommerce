package com.shop.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Map;

public record ProductRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "Máximo 100 caracteres")
        String name,

        @Size(max = 500, message = "Máximo 500 caracteres")
        String description,

        @NotBlank(message = "La categoria es obligatoria")
        String category,

        @NotNull(message = "El precio es obligatorio")
        @Positive(message = "El precio debe ser mayor a 0")
        BigDecimal price,
        Map<String, String> attributes
) {
}

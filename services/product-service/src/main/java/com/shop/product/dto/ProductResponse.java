package com.shop.product.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

public record ProductResponse(
        String id,                       // id generado por Mongo
        String name,
        String description,
        String category,
        BigDecimal price,
        Map<String, String> attributes,
        Instant createdAt                // cuándo se creó
) implements Serializable {}
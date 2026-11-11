package com.shop.product.service;

import com.shop.product.document.Product;
import com.shop.product.dto.ProductResponse;
import com.shop.product.exception.ProductNotFoundException;
import com.shop.product.mapper.ProductMapper;
import com.shop.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;



import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void findById_cuandoExiste_devuelveElProducto(){

        // GIVEN: preparo los datos y le digo a los mocks qué responder
        Product polera = Product.builder()
                .id("abc")
                .name("Polera negra")
                .price(new BigDecimal("40.00"))
                .build();

        ProductResponse respuesta = new ProductResponse(
                "abc", "Polera negra", null, null,
                new BigDecimal("40.00"), null, null);

        when(productRepository.findById("abc"))
                .thenReturn(Optional.of(polera));

        when(productMapper.toResponse(polera))
                .thenReturn(respuesta);

        // WHEN: ejecuto el método real que quiero probar
        ProductResponse resultado = productService.findById("abc");

        // THEN: verifico el resultado
        assertThat(resultado.name()).isEqualTo("Polera negra");
        assertThat(resultado.price()).isEqualByComparingTo("40.00");

    }

    @Test
    void findById_cuandoNoExiste_lanzaProductNotFoundException() {

        // GIVEN: el repository devuelve la caja vacía
        when(productRepository.findById("NO-EXISTE"))
                .thenReturn(Optional.empty());

        // WHEN + THEN: al buscar, debe lanzar la excepción
        assertThatThrownBy(() -> productService.findById("NO-EXISTE"))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("NO-EXISTE");

        verify(productMapper, never()).toResponse(any());   // el mapper NUNCA debió usarse
    }
}

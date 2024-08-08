package ru.tms.product.productservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tms.product.productservice.controller.dto.ProductDto;
import ru.tms.product.productservice.models.Product;
import ru.tms.product.productservice.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository repository;

    public void createProduct(ProductDto dto) {
        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .build();
        repository.save(product);
        log.info("Product is save {}", product.getName());
    }
}

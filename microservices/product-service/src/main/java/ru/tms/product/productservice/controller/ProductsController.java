package ru.tms.product.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tms.product.productservice.controller.dto.ProductDto;
import ru.tms.product.productservice.controller.dto.ProductResponseDto;
import ru.tms.product.productservice.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductService service;

    // curl -d '{"name":"name1", "description":"description1", "price": "1.23"}' -H "Content-Type: application/json" -X POST http://localhost:8080/api/product
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductDto product) {
        service.createProduct(product);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDto> getProducts() {
        return service.getProducts();
    }

}

package ru.tms.product.productservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tms.product.productservice.controller.models.ProductApi;
import ru.tms.product.productservice.models.Product;

@RestController
@RequestMapping("/api/product")
public class ProductsController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductApi product) {

    }

}

package ru.tms.product.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.tms.product.productservice.models.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}

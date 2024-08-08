package ru.tms.product.productservice.controller.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductApi {
    private String name;
    private String description;
    private BigDecimal price;
}

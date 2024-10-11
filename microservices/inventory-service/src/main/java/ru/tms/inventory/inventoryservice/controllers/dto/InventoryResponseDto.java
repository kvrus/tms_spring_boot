package ru.tms.inventory.inventoryservice.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponseDto {
    private Long id;
    private String skuCode;
    private Integer quantity;
}

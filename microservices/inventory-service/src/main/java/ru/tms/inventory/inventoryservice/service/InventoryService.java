package ru.tms.inventory.inventoryservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tms.inventory.inventoryservice.controllers.dto.InventoryResponseDto;
import ru.tms.inventory.inventoryservice.repository.InventoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    final private InventoryRepository repository;
    @Transactional(readOnly = true)
    public boolean isInStock(String sku) {
        return repository.findBySkuCode(sku).isPresent();
    }

    public List<InventoryResponseDto> getInventory() {
        return repository.findAll().stream().map(item -> new InventoryResponseDto(item.getId(), item.getSkuCode(), item.getQuantity())).toList();
    }
}

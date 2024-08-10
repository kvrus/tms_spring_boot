package ru.tms.inventory.inventoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tms.inventory.inventoryservice.models.Inventory;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findBySkuCode(String skuCode);
}

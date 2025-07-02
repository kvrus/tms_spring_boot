package ru.tms.inventory.inventoryservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.tms.inventory.inventoryservice.models.Inventory;
import ru.tms.inventory.inventoryservice.repository.InventoryRepository;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository repository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("iPhoneX");
			inventory.setQuantity(100);

			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("iPad3");
			inventory2.setQuantity(50);

			Inventory inventory3 = new Inventory();
			inventory3.setSkuCode("iPhone13");
			inventory3.setQuantity(10);

			if (!repository.existsBySkuCode(inventory.getSkuCode())) {
				repository.save(inventory);
			}

			if (!repository.existsBySkuCode(inventory2.getSkuCode())) {
				repository.save(inventory2);
			}

			if (!repository.existsBySkuCode(inventory3.getSkuCode())) {
				repository.save(inventory3);
			}
		};
	}

}

package com.learning.inventoryservice.repo;

import com.learning.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepo extends JpaRepository<Inventory , Long > {

    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}

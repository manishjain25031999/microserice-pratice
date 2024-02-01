package com.learning.inventoryservice.service;

import com.learning.inventoryservice.dto.InventoryResponse;
import com.learning.inventoryservice.repo.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
   private InventoryRepo inventoryRepo ;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode)
    {
        return inventoryRepo.findBySkuCodeIn(skuCode).stream()
                .map(inventory -> InventoryResponse.builder()
                        .skuCode(inventory.getSkuCode())
                        .isInStock(inventory.getQuantity() >0)
                        .build()
                ).toList();
    }

}

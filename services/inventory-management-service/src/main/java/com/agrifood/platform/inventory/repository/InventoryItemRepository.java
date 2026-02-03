package com.agrifood.platform.inventory.repository;

import com.agrifood.platform.inventory.entity.InventoryItem;
import com.agrifood.platform.inventory.enums.ProductCategory;
import com.agrifood.platform.inventory.enums.InventoryStatus;
import com.agrifood.platform.inventory.enums.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findBySku(String sku);
    List<InventoryItem> findByCategory(ProductCategory category);
    List<InventoryItem> findByType(ProductType type);
    List<InventoryItem> findByStatus(InventoryStatus status);
    
    @Query("SELECT i FROM InventoryItem i WHERE i.quantityOnHand < i.reorderLevel AND i.status = com.agrifood.platform.inventory.enums.InventoryStatus.ACTIVE")
    List<InventoryItem> findBelowReorderLevel();
}
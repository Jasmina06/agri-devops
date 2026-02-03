package com.agrifood.platform.inventory.service;

import com.agrifood.platform.inventory.entity.InventoryItem;
import com.agrifood.platform.inventory.repository.InventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryItemService {
    
    @Autowired
    private InventoryItemRepository inventoryItemRepository;
    
    public List<InventoryItem> getAllInventoryItems() {
        return inventoryItemRepository.findAll();
    }
    
    public Optional<InventoryItem> getInventoryItemById(Long id) {
        return inventoryItemRepository.findById(id);
    }
    
    public Optional<InventoryItem> getInventoryItemBySku(String sku) {
        return inventoryItemRepository.findBySku(sku);
    }
    
    public InventoryItem createInventoryItem(InventoryItem inventoryItem) {
        // Validate inventory item data
        validateInventoryItem(inventoryItem);
        
        // Check if SKU already exists
        if (inventoryItemRepository.findBySku(inventoryItem.getSku()).isPresent()) {
            throw new RuntimeException("Inventory item with SKU " + inventoryItem.getSku() + " already exists");
        }
        
        // Set initial status
        inventoryItem.setStatus(com.agrifood.platform.inventory.enums.InventoryStatus.ACTIVE);
        
        return inventoryItemRepository.save(inventoryItem);
    }
    
    public InventoryItem updateInventoryItem(Long id, InventoryItem inventoryItemDetails) {
        InventoryItem inventoryItem = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with id: " + id));
        
        // Update fields
        inventoryItem.setItemName(inventoryItemDetails.getItemName());
        inventoryItem.setSku(inventoryItemDetails.getSku());
        inventoryItem.setDescription(inventoryItemDetails.getDescription());
        inventoryItem.setCategory(inventoryItemDetails.getCategory());
        inventoryItem.setType(inventoryItemDetails.getType());
        inventoryItem.setBrand(inventoryItemDetails.getBrand());
        inventoryItem.setOrigin(inventoryItemDetails.getOrigin());
        inventoryItem.setUnitPrice(inventoryItemDetails.getUnitPrice());
        inventoryItem.setQuantityOnHand(inventoryItemDetails.getQuantityOnHand());
        inventoryItem.setReorderLevel(inventoryItemDetails.getReorderLevel());
        inventoryItem.setStorageLocation(inventoryItemDetails.getStorageLocation());
        inventoryItem.setAttributes(inventoryItemDetails.getAttributes());
        inventoryItem.setSpecifications(inventoryItemDetails.getSpecifications());
        inventoryItem.setSupplier(inventoryItemDetails.getSupplier());
        inventoryItem.setFarmer(inventoryItemDetails.getFarmer());
        inventoryItem.setUpdatedAt(java.time.LocalDateTime.now());
        
        return inventoryItemRepository.save(inventoryItem);
    }
    
    public void deleteInventoryItem(Long id) {
        InventoryItem inventoryItem = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with id: " + id));
        inventoryItemRepository.delete(inventoryItem);
    }
    
    public List<InventoryItem> getInventoryItemsByCategory(com.agrifood.platform.inventory.entity.ProductCategory category) {
        return inventoryItemRepository.findByCategory(category);
    }
    
    public List<InventoryItem> getInventoryItemsByType(com.agrifood.platform.inventory.entity.ProductType type) {
        return inventoryItemRepository.findByType(type);
    }
    
    public List<InventoryItem> getInventoryItemsByStatus(com.agrifood.platform.inventory.entity.InventoryStatus status) {
        return inventoryItemRepository.findByStatus(status);
    }
    
    public List<InventoryItem> getInventoryItemsBelowReorderLevel() {
        return inventoryItemRepository.findBelowReorderLevel();
    }
    
    public InventoryItem updateQuantity(Long id, Integer newQuantity) {
        InventoryItem inventoryItem = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with id: " + id));
        
        inventoryItem.setQuantityOnHand(newQuantity);
        inventoryItem.setAvailableQuantity(newQuantity - (inventoryItem.getReservedQuantity() != null ? inventoryItem.getReservedQuantity() : 0));
        inventoryItem.setUpdatedAt(java.time.LocalDateTime.now());
        
        return inventoryItemRepository.save(inventoryItem);
    }
    
    public InventoryItem reserveItem(Long id, Integer quantity) {
        InventoryItem inventoryItem = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with id: " + id));
        
        Integer available = inventoryItem.getAvailableQuantity();
        if (available < quantity) {
            throw new RuntimeException("Insufficient inventory. Available: " + available + ", Requested: " + quantity);
        }
        
        Integer currentReserved = inventoryItem.getReservedQuantity() != null ? inventoryItem.getReservedQuantity() : 0;
        inventoryItem.setReservedQuantity(currentReserved + quantity);
        inventoryItem.setAvailableQuantity(available - quantity);
        inventoryItem.setUpdatedAt(java.time.LocalDateTime.now());
        
        return inventoryItemRepository.save(inventoryItem);
    }
    
    public InventoryItem releaseReservation(Long id, Integer quantity) {
        InventoryItem inventoryItem = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with id: " + id));
        
        Integer currentReserved = inventoryItem.getReservedQuantity() != null ? inventoryItem.getReservedQuantity() : 0;
        if (currentReserved < quantity) {
            throw new RuntimeException("Cannot release more than reserved. Reserved: " + currentReserved + ", Requested to release: " + quantity);
        }
        
        inventoryItem.setReservedQuantity(currentReserved - quantity);
        inventoryItem.setAvailableQuantity(inventoryItem.getAvailableQuantity() + quantity);
        inventoryItem.setUpdatedAt(java.time.LocalDateTime.now());
        
        return inventoryItemRepository.save(inventoryItem);
    }
    
    private void validateInventoryItem(InventoryItem inventoryItem) {
        if (inventoryItem.getItemName() == null || inventoryItem.getItemName().trim().isEmpty()) {
            throw new RuntimeException("Item name is required");
        }
        if (inventoryItem.getSku() == null || inventoryItem.getSku().trim().isEmpty()) {
            throw new RuntimeException("SKU is required");
        }
        if (inventoryItem.getCategory() == null) {
            throw new RuntimeException("Category is required");
        }
        if (inventoryItem.getType() == null) {
            throw new RuntimeException("Product type is required");
        }
        if (inventoryItem.getUnitPrice() == null || inventoryItem.getUnitPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valid unit price is required");
        }
        if (inventoryItem.getQuantityOnHand() == null || inventoryItem.getQuantityOnHand() < 0) {
            throw new RuntimeException("Valid quantity on hand is required");
        }
        if (inventoryItem.getReorderLevel() == null || inventoryItem.getReorderLevel() < 0) {
            throw new RuntimeException("Valid reorder level is required");
        }
    }
}
package com.agrifood.platform.inventory.controller;

import com.agrifood.platform.inventory.entity.InventoryItem;
import com.agrifood.platform.inventory.service.InventoryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryItemController {
    
    @Autowired
    private InventoryItemService inventoryItemService;
    
    @GetMapping
    public ResponseEntity<List<InventoryItem>> getAllInventoryItems() {
        List<InventoryItem> inventoryItems = inventoryItemService.getAllInventoryItems();
        return ResponseEntity.ok(inventoryItems);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<InventoryItem> getInventoryItemById(@PathVariable Long id) {
        return inventoryItemService.getInventoryItemById(id)
                .map(item -> ResponseEntity.ok(item))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/sku/{sku}")
    public ResponseEntity<InventoryItem> getInventoryItemBySku(@PathVariable String sku) {
        return inventoryItemService.getInventoryItemBySku(sku)
                .map(item -> ResponseEntity.ok(item))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<InventoryItem> createInventoryItem(@RequestBody InventoryItem inventoryItem) {
        try {
            InventoryItem createdItem = inventoryItemService.createInventoryItem(inventoryItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<InventoryItem> updateInventoryItem(@PathVariable Long id, @RequestBody InventoryItem inventoryItemDetails) {
        try {
            InventoryItem updatedItem = inventoryItemService.updateInventoryItem(id, inventoryItemDetails);
            return ResponseEntity.ok(updatedItem);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable Long id) {
        try {
            inventoryItemService.deleteInventoryItem(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<InventoryItem>> getInventoryItemsByCategory(@PathVariable com.agrifood.platform.inventory.entity.ProductCategory category) {
        List<InventoryItem> items = inventoryItemService.getInventoryItemsByCategory(category);
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<InventoryItem>> getInventoryItemsByType(@PathVariable com.agrifood.platform.inventory.entity.ProductType type) {
        List<InventoryItem> items = inventoryItemService.getInventoryItemsByType(type);
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<InventoryItem>> getInventoryItemsByStatus(@PathVariable com.agrifood.platform.inventory.entity.InventoryStatus status) {
        List<InventoryItem> items = inventoryItemService.getInventoryItemsByStatus(status);
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryItem>> getInventoryItemsBelowReorderLevel() {
        List<InventoryItem> items = inventoryItemService.getInventoryItemsBelowReorderLevel();
        return ResponseEntity.ok(items);
    }
    
    @PatchMapping("/{id}/quantity/{quantity}")
    public ResponseEntity<InventoryItem> updateQuantity(@PathVariable Long id, @PathVariable Integer quantity) {
        try {
            InventoryItem updatedItem = inventoryItemService.updateQuantity(id, quantity);
            return ResponseEntity.ok(updatedItem);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PatchMapping("/{id}/reserve/{quantity}")
    public ResponseEntity<InventoryItem> reserveItem(@PathVariable Long id, @PathVariable Integer quantity) {
        try {
            InventoryItem updatedItem = inventoryItemService.reserveItem(id, quantity);
            return ResponseEntity.ok(updatedItem);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PatchMapping("/{id}/release/{quantity}")
    public ResponseEntity<InventoryItem> releaseReservation(@PathVariable Long id, @PathVariable Integer quantity) {
        try {
            InventoryItem updatedItem = inventoryItemService.releaseReservation(id, quantity);
            return ResponseEntity.ok(updatedItem);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
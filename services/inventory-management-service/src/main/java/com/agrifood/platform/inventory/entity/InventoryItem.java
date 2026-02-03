package com.agrifood.platform.inventory.entity;

import com.agrifood.platform.inventory.enums.ProductCategory;
import com.agrifood.platform.inventory.enums.ProductType;
import com.agrifood.platform.inventory.enums.InventoryStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "inventory_items")
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(unique = true, nullable = false)
    private String sku;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    private String brand;

    private String origin;

    private BigDecimal unitPrice;

    private Integer quantityOnHand;

    private Integer reservedQuantity;

    private Integer availableQuantity;

    private Integer reorderLevel;

    private String storageLocation;

    @ElementCollection
    @CollectionTable(name = "inventory_attributes", joinColumns = @JoinColumn(name = "item_id"))
    @MapKeyColumn(name = "attribute_name")
    @Column(name = "attribute_value")
    private List<String> attributes;

    @Column(columnDefinition = "TEXT")
    private String specifications;

    @Enumerated(EnumType.STRING)
    private InventoryStatus status;

    private LocalDateTime manufacturedDate;

    private LocalDateTime expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private com.agrifood.platform.supplier.entity.Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmer_id")
    private com.agrifood.platform.farmer.entity.Farmer farmer;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Constructors
    public InventoryItem() {}

    public InventoryItem(String itemName, String sku, String description, ProductCategory category,
                         ProductType type, String brand, String origin, BigDecimal unitPrice,
                         Integer quantityOnHand, Integer reorderLevel, String storageLocation,
                         com.agrifood.platform.supplier.entity.Supplier supplier,
                         com.agrifood.platform.farmer.entity.Farmer farmer) {
        this.itemName = itemName;
        this.sku = sku;
        this.description = description;
        this.category = category;
        this.type = type;
        this.brand = brand;
        this.origin = origin;
        this.unitPrice = unitPrice;
        this.quantityOnHand = quantityOnHand != null ? quantityOnHand : 0;
        this.reservedQuantity = 0;
        this.availableQuantity = quantityOnHand != null ? quantityOnHand : 0;
        this.reorderLevel = reorderLevel != null ? reorderLevel : 10;
        this.storageLocation = storageLocation;
        this.status = InventoryStatus.ACTIVE;
        this.supplier = supplier;
        this.farmer = farmer;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
        this.availableQuantity = quantityOnHand - (reservedQuantity != null ? reservedQuantity : 0);
    }

    public Integer getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(Integer reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
        this.availableQuantity = (quantityOnHand != null ? quantityOnHand : 0) - (reservedQuantity != null ? reservedQuantity : 0);
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public InventoryStatus getStatus() {
        return status;
    }

    public void setStatus(InventoryStatus status) {
        this.status = status;
    }

    public LocalDateTime getManufacturedDate() {
        return manufacturedDate;
    }

    public void setManufacturedDate(LocalDateTime manufacturedDate) {
        this.manufacturedDate = manufacturedDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public com.agrifood.platform.supplier.entity.Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(com.agrifood.platform.supplier.entity.Supplier supplier) {
        this.supplier = supplier;
    }

    public com.agrifood.platform.farmer.entity.Farmer getFarmer() {
        return farmer;
    }

    public void setFarmer(com.agrifood.platform.farmer.entity.Farmer farmer) {
        this.farmer = farmer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
package com.agrifood.platform.supplier.entity;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class ProductPricing {
    private String productName;
    private BigDecimal price;
    
    public ProductPricing() {}
    
    public ProductPricing(String productName, BigDecimal price) {
        this.productName = productName;
        this.price = price;
    }
    
    // Getters and setters
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
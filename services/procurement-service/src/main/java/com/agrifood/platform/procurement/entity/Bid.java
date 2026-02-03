package com.agrifood.platform.procurement.entity;

import com.agrifood.platform.procurement.enums.BidStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bids")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procurement_id")
    private Procurement procurement;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private com.agrifood.platform.supplier.entity.Supplier supplier;
    
    private BigDecimal bidAmount;
    
    @Column(columnDefinition = "TEXT")
    private String proposal;
    
    private LocalDateTime submittedAt;
    
    @Enumerated(EnumType.STRING)
    private BidStatus status;
    
    // Constructors
    public Bid() {}
    
    public Bid(Procurement procurement, com.agrifood.platform.supplier.entity.Supplier supplier, 
               BigDecimal bidAmount, String proposal) {
        this.procurement = procurement;
        this.supplier = supplier;
        this.bidAmount = bidAmount;
        this.proposal = proposal;
        this.submittedAt = LocalDateTime.now();
        this.status = BidStatus.SUBMITTED;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Procurement getProcurement() {
        return procurement;
    }
    
    public void setProcurement(Procurement procurement) {
        this.procurement = procurement;
    }
    
    public com.agrifood.platform.supplier.entity.Supplier getSupplier() {
        return supplier;
    }
    
    public void setSupplier(com.agrifood.platform.supplier.entity.Supplier supplier) {
        this.supplier = supplier;
    }
    
    public BigDecimal getBidAmount() {
        return bidAmount;
    }
    
    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }
    
    public String getProposal() {
        return proposal;
    }
    
    public void setProposal(String proposal) {
        this.proposal = proposal;
    }
    
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
    
    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
    
    public BidStatus getStatus() {
        return status;
    }
    
    public void setStatus(BidStatus status) {
        this.status = status;
    }
}
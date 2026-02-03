package com.agrifood.platform.procurement.entity;

import com.agrifood.platform.procurement.enums.ContractStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procurement_id")
    private Procurement procurement;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private com.agrifood.platform.supplier.entity.Supplier supplier;
    
    private BigDecimal contractValue;
    
    private LocalDateTime signedDate;
    
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;
    
    @Column(columnDefinition = "TEXT")
    private String terms;
    
    @Enumerated(EnumType.STRING)
    private ContractStatus status;
    
    // Constructors
    public Contract() {}
    
    public Contract(Procurement procurement, com.agrifood.platform.supplier.entity.Supplier supplier, 
                   BigDecimal contractValue, LocalDateTime startDate, LocalDateTime endDate) {
        this.procurement = procurement;
        this.supplier = supplier;
        this.contractValue = contractValue;
        this.signedDate = LocalDateTime.now();
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = ContractStatus.ACTIVE;
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
    
    public BigDecimal getContractValue() {
        return contractValue;
    }
    
    public void setContractValue(BigDecimal contractValue) {
        this.contractValue = contractValue;
    }
    
    public LocalDateTime getSignedDate() {
        return signedDate;
    }
    
    public void setSignedDate(LocalDateTime signedDate) {
        this.signedDate = signedDate;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    
    public LocalDateTime getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    
    public String getTerms() {
        return terms;
    }
    
    public void setTerms(String terms) {
        this.terms = terms;
    }
    
    public ContractStatus getStatus() {
        return status;
    }
    
    public void setStatus(ContractStatus status) {
        this.status = status;
    }
}
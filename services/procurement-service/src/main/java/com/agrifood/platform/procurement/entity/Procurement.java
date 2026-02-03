package com.agrifood.platform.procurement.entity;

import com.agrifood.platform.procurement.enums.ProcurementType;
import com.agrifood.platform.procurement.enums.ProcurementStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "procurements")
public class Procurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String procurementNumber;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private ProcurementType type;

    @Enumerated(EnumType.STRING)
    private ProcurementStatus status;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_farmer_id")
    private com.agrifood.platform.farmer.entity.Farmer createdBy;

    @OneToMany(mappedBy = "procurement", cascade = CascadeType.ALL)
    private List<Bid> bids;

    @OneToMany(mappedBy = "procurement", cascade = CascadeType.ALL)
    private List<Contract> contracts;

    private BigDecimal estimatedValue;

    private BigDecimal awardedValue;

    @ElementCollection
    @CollectionTable(name = "procurement_products", joinColumns = @JoinColumn(name = "procurement_id"))
    @Column(name = "product")
    private List<String> requiredProducts;

    @Column(columnDefinition = "TEXT")
    private String termsAndConditions;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Constructors
    public Procurement() {}

    public Procurement(String title, String description, ProcurementType type, LocalDateTime startDate,
                       LocalDateTime endDate, com.agrifood.platform.farmer.entity.Farmer createdBy,
                       BigDecimal estimatedValue, List<String> requiredProducts) {
        this.procurementNumber = generateProcurementNumber();
        this.title = title;
        this.description = description;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdBy = createdBy;
        this.estimatedValue = estimatedValue;
        this.requiredProducts = requiredProducts;
        this.status = ProcurementStatus.DRAFT;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    private String generateProcurementNumber() {
        // Generate a unique procurement number
        return "PROC-" + System.currentTimeMillis();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcurementNumber() {
        return procurementNumber;
    }

    public void setProcurementNumber(String procurementNumber) {
        this.procurementNumber = procurementNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProcurementType getType() {
        return type;
    }

    public void setType(ProcurementType type) {
        this.type = type;
    }

    public ProcurementStatus getStatus() {
        return status;
    }

    public void setStatus(ProcurementStatus status) {
        this.status = status;
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

    public com.agrifood.platform.farmer.entity.Farmer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(com.agrifood.platform.farmer.entity.Farmer createdBy) {
        this.createdBy = createdBy;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public BigDecimal getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(BigDecimal estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public BigDecimal getAwardedValue() {
        return awardedValue;
    }

    public void setAwardedValue(BigDecimal awardedValue) {
        this.awardedValue = awardedValue;
    }

    public List<String> getRequiredProducts() {
        return requiredProducts;
    }

    public void setRequiredProducts(List<String> requiredProducts) {
        this.requiredProducts = requiredProducts;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
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
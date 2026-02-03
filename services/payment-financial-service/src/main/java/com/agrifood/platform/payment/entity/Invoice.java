package com.agrifood.platform.payment.entity;

import com.agrifood.platform.payment.enums.InvoiceStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
public class Invoice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String invoiceNumber;
    
    @Column(nullable = false)
    private String orderId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private com.agrifood.platform.supplier.entity.Supplier supplier;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private com.agrifood.platform.farmer.entity.Farmer customer;
    
    @Column(nullable = false)
    private BigDecimal totalAmount;
    
    private BigDecimal taxAmount;
    
    private BigDecimal discountAmount;
    
    private String currency;
    
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;
    
    private LocalDateTime issueDate;
    
    private LocalDateTime dueDate;
    
    private LocalDateTime paidDate;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(columnDefinition = "TEXT")
    private String terms;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

    // Constructors
    public Invoice() {}
    
    public Invoice(String orderId, com.agrifood.platform.supplier.entity.Supplier supplier,
                   com.agrifood.platform.farmer.entity.Farmer customer, BigDecimal totalAmount,
                   String currency, LocalDateTime dueDate) {
        this.invoiceNumber = generateInvoiceNumber();
        this.orderId = orderId;
        this.supplier = supplier;
        this.customer = customer;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.issueDate = LocalDateTime.now();
        this.dueDate = dueDate;
        this.status = InvoiceStatus.DRAFT;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    private String generateInvoiceNumber() {
        // Generate a unique invoice number
        return "INV-" + System.currentTimeMillis();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public com.agrifood.platform.supplier.entity.Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(com.agrifood.platform.supplier.entity.Supplier supplier) {
        this.supplier = supplier;
    }

    public com.agrifood.platform.farmer.entity.Farmer getCustomer() {
        return customer;
    }

    public void setCustomer(com.agrifood.platform.farmer.entity.Farmer customer) {
        this.customer = customer;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDateTime paidDate) {
        this.paidDate = paidDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
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
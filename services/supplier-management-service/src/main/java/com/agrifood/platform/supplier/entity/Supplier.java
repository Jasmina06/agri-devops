package com.agrifood.platform.supplier.entity;

import com.agrifood.platform.supplier.enums.SupplierStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String contactPerson;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    @Column(length = 500)
    private String address;

    @ElementCollection
    @CollectionTable(name = "supplier_products", joinColumns = @JoinColumn(name = "supplier_id"))
    @Column(name = "product")
    private List<String> products;

    @ElementCollection
    @CollectionTable(name = "supplier_pricing", joinColumns = @JoinColumn(name = "supplier_id"))
    @AttributeOverrides({
        @AttributeOverride(name = "productName", column = @Column(name = "product_name")),
        @AttributeOverride(name = "price", column = @Column(name = "price"))
    })
    private List<ProductPricing> pricing;

    private String region;

    private Boolean isActive;

    @Column(columnDefinition = "TEXT")
    private String certifications;

    @Enumerated(EnumType.STRING)
    private SupplierStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Constructors
    public Supplier() {}

    public Supplier(String companyName, String contactPerson, String email, String phone, String address,
                    List<String> products, String region, Boolean isActive) {
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.products = products;
        this.region = region;
        this.isActive = isActive != null ? isActive : true;
        this.status = SupplierStatus.PENDING;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public List<ProductPricing> getPricing() {
        return pricing;
    }

    public void setPricing(List<ProductPricing> pricing) {
        this.pricing = pricing;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public String getCertifications() {
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }

    public SupplierStatus getStatus() {
        return status;
    }

    public void setStatus(SupplierStatus status) {
        this.status = status;
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
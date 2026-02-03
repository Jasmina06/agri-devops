package com.agrifood.platform.supplier.service;

import com.agrifood.platform.supplier.entity.Supplier;
import com.agrifood.platform.supplier.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    
    @Autowired
    private SupplierRepository supplierRepository;
    
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }
    
    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }
    
    public Optional<Supplier> getSupplierByEmail(String email) {
        return supplierRepository.findByEmail(email);
    }
    
    public Supplier createSupplier(Supplier supplier) {
        // Validate supplier data
        validateSupplier(supplier);
        
        // Check if supplier with email already exists
        if (supplierRepository.findByEmail(supplier.getEmail()).isPresent()) {
            throw new RuntimeException("Supplier with email " + supplier.getEmail() + " already exists");
        }
        
        // Set default status
        supplier.setStatus(com.agrifood.platform.supplier.enums.SupplierStatus.PENDING);
        supplier.setIsActive(true);
        
        return supplierRepository.save(supplier);
    }
    
    public Supplier updateSupplier(Long id, Supplier supplierDetails) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
        
        // Update fields
        supplier.setCompanyName(supplierDetails.getCompanyName());
        supplier.setContactPerson(supplierDetails.getContactPerson());
        supplier.setEmail(supplierDetails.getEmail());
        supplier.setPhone(supplierDetails.getPhone());
        supplier.setAddress(supplierDetails.getAddress());
        supplier.setProducts(supplierDetails.getProducts());
        supplier.setPricing(supplierDetails.getPricing());
        supplier.setRegion(supplierDetails.getRegion());
        supplier.setIsActive(supplierDetails.getIsActive());
        supplier.setCertifications(supplierDetails.getCertifications());
        supplier.setStatus(supplierDetails.getStatus());
        supplier.setUpdatedAt(java.time.LocalDateTime.now());
        
        return supplierRepository.save(supplier);
    }
    
    public void deleteSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
        supplierRepository.delete(supplier);
    }
    
    public List<Supplier> getSuppliersByRegion(String region) {
        return supplierRepository.findByRegion(region);
    }
    
    public List<Supplier> getSuppliersByProduct(String product) {
        return supplierRepository.findByProductsContaining(product);
    }
    
    public List<Supplier> getSuppliersByStatus(com.agrifood.platform.supplier.entity.SupplierStatus status) {
        return supplierRepository.findByStatus(status);
    }
    
    public List<Supplier> getActiveSuppliers() {
        return supplierRepository.findByIsActiveTrue();
    }
    
    private void validateSupplier(Supplier supplier) {
        if (supplier.getCompanyName() == null || supplier.getCompanyName().trim().isEmpty()) {
            throw new RuntimeException("Company name is required");
        }
        if (supplier.getContactPerson() == null || supplier.getContactPerson().trim().isEmpty()) {
            throw new RuntimeException("Contact person is required");
        }
        if (supplier.getEmail() == null || supplier.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }
        if (!isValidEmail(supplier.getEmail())) {
            throw new RuntimeException("Invalid email format");
        }
        if (supplier.getProducts() == null || supplier.getProducts().isEmpty()) {
            throw new RuntimeException("At least one product is required");
        }
    }
    
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
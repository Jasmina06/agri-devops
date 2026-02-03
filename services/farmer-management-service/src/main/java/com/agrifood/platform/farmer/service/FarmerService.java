package com.agrifood.platform.farmer.service;

import com.agrifood.platform.farmer.entity.Farmer;
import com.agrifood.platform.farmer.repository.FarmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FarmerService {
    
    @Autowired
    private FarmerRepository farmerRepository;
    
    public List<Farmer> getAllFarmers() {
        return farmerRepository.findAll();
    }
    
    public Optional<Farmer> getFarmerById(Long id) {
        return farmerRepository.findById(id);
    }
    
    public Optional<Farmer> getFarmerByEmail(String email) {
        return farmerRepository.findByEmail(email);
    }
    
    public Farmer createFarmer(Farmer farmer) {
        // Validate farmer data
        validateFarmer(farmer);
        
        // Check if farmer with email already exists
        if (farmerRepository.findByEmail(farmer.getEmail()).isPresent()) {
            throw new RuntimeException("Farmer with email " + farmer.getEmail() + " already exists");
        }
        
        // Set default status
        farmer.setStatus(com.agrifood.platform.farmer.enums.FarmerStatus.ACTIVE);
        
        return farmerRepository.save(farmer);
    }
    
    public Farmer updateFarmer(Long id, Farmer farmerDetails) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farmer not found with id: " + id));
        
        // Update fields
        farmer.setFirstName(farmerDetails.getFirstName());
        farmer.setLastName(farmerDetails.getLastName());
        farmer.setEmail(farmerDetails.getEmail());
        farmer.setPhone(farmerDetails.getPhone());
        farmer.setAddress(farmerDetails.getAddress());
        farmer.setFarmName(farmerDetails.getFarmName());
        farmer.setFarmSize(farmerDetails.getFarmSize());
        farmer.setProducts(farmerDetails.getProducts());
        farmer.setRegion(farmerDetails.getRegion());
        farmer.setDeliveryCapability(farmerDetails.getDeliveryCapability());
        farmer.setStatus(farmerDetails.getStatus());
        farmer.setUpdatedAt(java.time.LocalDateTime.now());
        
        return farmerRepository.save(farmer);
    }
    
    public void deleteFarmer(Long id) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farmer not found with id: " + id));
        farmerRepository.delete(farmer);
    }
    
    public List<Farmer> getFarmersByRegion(String region) {
        return farmerRepository.findByRegion(region);
    }
    
    public List<Farmer> getFarmersByProduct(String product) {
        return farmerRepository.findByProductsContaining(product);
    }
    
    public List<Farmer> getFarmersByStatus(com.agrifood.platform.farmer.entity.FarmerStatus status) {
        return farmerRepository.findByStatus(status);
    }
    
    private void validateFarmer(Farmer farmer) {
        if (farmer.getFirstName() == null || farmer.getFirstName().trim().isEmpty()) {
            throw new RuntimeException("First name is required");
        }
        if (farmer.getLastName() == null || farmer.getLastName().trim().isEmpty()) {
            throw new RuntimeException("Last name is required");
        }
        if (farmer.getEmail() == null || farmer.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }
        if (!isValidEmail(farmer.getEmail())) {
            throw new RuntimeException("Invalid email format");
        }
        if (farmer.getFarmName() == null || farmer.getFarmName().trim().isEmpty()) {
            throw new RuntimeException("Farm name is required");
        }
    }
    
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
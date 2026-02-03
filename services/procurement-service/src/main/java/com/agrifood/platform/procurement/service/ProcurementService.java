package com.agrifood.platform.procurement.service;

import com.agrifood.platform.procurement.entity.*;
import com.agrifood.platform.procurement.repository.ProcurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProcurementService {
    
    @Autowired
    private ProcurementRepository procurementRepository;
    
    public List<Procurement> getAllProcurements() {
        return procurementRepository.findAll();
    }
    
    public Optional<Procurement> getProcurementById(Long id) {
        return procurementRepository.findById(id);
    }
    
    public Procurement createProcurement(Procurement procurement) {
        // Validate procurement data
        validateProcurement(procurement);
        
        // Set initial status
        procurement.setStatus(com.agrifood.platform.procurement.enums.ProcurementStatus.DRAFT);
        
        return procurementRepository.save(procurement);
    }
    
    public Procurement updateProcurement(Long id, Procurement procurementDetails) {
        Procurement procurement = procurementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Procurement not found with id: " + id));
        
        // Update fields
        procurement.setTitle(procurementDetails.getTitle());
        procurement.setDescription(procurementDetails.getDescription());
        procurement.setType(procurementDetails.getType());
        procurement.setStartDate(procurementDetails.getStartDate());
        procurement.setEndDate(procurementDetails.getEndDate());
        procurement.setCreatedBy(procurementDetails.getCreatedBy());
        procurement.setEstimatedValue(procurementDetails.getEstimatedValue());
        procurement.setRequiredProducts(procurementDetails.getRequiredProducts());
        procurement.setTermsAndConditions(procurementDetails.getTermsAndConditions());
        procurement.setUpdatedAt(java.time.LocalDateTime.now());
        
        return procurementRepository.save(procurement);
    }
    
    public void deleteProcurement(Long id) {
        Procurement procurement = procurementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Procurement not found with id: " + id));
        procurementRepository.delete(procurement);
    }
    
    public Procurement publishProcurement(Long id) {
        Procurement procurement = procurementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Procurement not found with id: " + id));
        
        if (procurement.getStatus() != ProcurementStatus.DRAFT) {
            throw new RuntimeException("Only draft procurements can be published");
        }
        
        procurement.setStatus(ProcurementStatus.PUBLISHED);
        procurement.setUpdatedAt(java.time.LocalDateTime.now());
        
        return procurementRepository.save(procurement);
    }
    
    public Procurement awardProcurement(Long id, Long supplierId) {
        Procurement procurement = procurementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Procurement not found with id: " + id));
        
        if (procurement.getStatus() != ProcurementStatus.EVALUATION) {
            throw new RuntimeException("Procurement must be in evaluation status to award");
        }
        
        // Find winning bid
        Optional<Bid> winningBid = procurement.getBids().stream()
                .filter(bid -> bid.getSupplier().getId().equals(supplierId))
                .findFirst();
        
        if (winningBid.isEmpty()) {
            throw new RuntimeException("Supplier not found in bids for this procurement");
        }
        
        // Update procurement status and awarded value
        procurement.setStatus(ProcurementStatus.AWARDED);
        procurement.setAwardedValue(winningBid.get().getBidAmount());
        procurement.setUpdatedAt(java.time.LocalDateTime.now());
        
        return procurementRepository.save(procurement);
    }
    
    private void validateProcurement(Procurement procurement) {
        if (procurement.getTitle() == null || procurement.getTitle().trim().isEmpty()) {
            throw new RuntimeException("Title is required");
        }
        if (procurement.getType() == null) {
            throw new RuntimeException("Procurement type is required");
        }
        if (procurement.getStartDate() == null) {
            throw new RuntimeException("Start date is required");
        }
        if (procurement.getEndDate() == null) {
            throw new RuntimeException("End date is required");
        }
        if (procurement.getCreatedBy() == null) {
            throw new RuntimeException("Created by farmer is required");
        }
        if (procurement.getEstimatedValue() == null) {
            throw new RuntimeException("Estimated value is required");
        }
        if (procurement.getRequiredProducts() == null || procurement.getRequiredProducts().isEmpty()) {
            throw new RuntimeException("At least one required product is required");
        }
        if (procurement.getStartDate().isAfter(procurement.getEndDate())) {
            throw new RuntimeException("Start date cannot be after end date");
        }
    }
}
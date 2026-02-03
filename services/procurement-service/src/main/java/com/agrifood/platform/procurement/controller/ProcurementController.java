package com.agrifood.platform.procurement.controller;

import com.agrifood.platform.procurement.entity.Procurement;
import com.agrifood.platform.procurement.service.ProcurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/procurements")
public class ProcurementController {
    
    @Autowired
    private ProcurementService procurementService;
    
    @GetMapping
    public ResponseEntity<List<Procurement>> getAllProcurements() {
        List<Procurement> procurements = procurementService.getAllProcurements();
        return ResponseEntity.ok(procurements);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Procurement> getProcurementById(@PathVariable Long id) {
        return procurementService.getProcurementById(id)
                .map(procurement -> ResponseEntity.ok(procurement))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Procurement> createProcurement(@RequestBody Procurement procurement) {
        try {
            Procurement createdProcurement = procurementService.createProcurement(procurement);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProcurement);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Procurement> updateProcurement(@PathVariable Long id, @RequestBody Procurement procurementDetails) {
        try {
            Procurement updatedProcurement = procurementService.updateProcurement(id, procurementDetails);
            return ResponseEntity.ok(updatedProcurement);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcurement(@PathVariable Long id) {
        try {
            procurementService.deleteProcurement(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}/publish")
    public ResponseEntity<Procurement> publishProcurement(@PathVariable Long id) {
        try {
            Procurement updatedProcurement = procurementService.publishProcurement(id);
            return ResponseEntity.ok(updatedProcurement);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PatchMapping("/{id}/award/{supplierId}")
    public ResponseEntity<Procurement> awardProcurement(@PathVariable Long id, @PathVariable Long supplierId) {
        try {
            Procurement updatedProcurement = procurementService.awardProcurement(id, supplierId);
            return ResponseEntity.ok(updatedProcurement);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
package com.agrifood.platform.farmer.controller;

import com.agrifood.platform.farmer.entity.Farmer;
import com.agrifood.platform.farmer.service.FarmerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/farmers")
public class FarmerController {
    
    @Autowired
    private FarmerService farmerService;
    
    @GetMapping
    public ResponseEntity<List<Farmer>> getAllFarmers() {
        List<Farmer> farmers = farmerService.getAllFarmers();
        return ResponseEntity.ok(farmers);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Farmer> getFarmerById(@PathVariable Long id) {
        return farmerService.getFarmerById(id)
                .map(farmer -> ResponseEntity.ok(farmer))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<Farmer> getFarmerByEmail(@PathVariable String email) {
        return farmerService.getFarmerByEmail(email)
                .map(farmer -> ResponseEntity.ok(farmer))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Farmer> createFarmer(@RequestBody Farmer farmer) {
        try {
            Farmer createdFarmer = farmerService.createFarmer(farmer);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFarmer);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Farmer> updateFarmer(@PathVariable Long id, @RequestBody Farmer farmerDetails) {
        try {
            Farmer updatedFarmer = farmerService.updateFarmer(id, farmerDetails);
            return ResponseEntity.ok(updatedFarmer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarmer(@PathVariable Long id) {
        try {
            farmerService.deleteFarmer(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/region/{region}")
    public ResponseEntity<List<Farmer>> getFarmersByRegion(@PathVariable String region) {
        List<Farmer> farmers = farmerService.getFarmersByRegion(region);
        return ResponseEntity.ok(farmers);
    }
    
    @GetMapping("/product/{product}")
    public ResponseEntity<List<Farmer>> getFarmersByProduct(@PathVariable String product) {
        List<Farmer> farmers = farmerService.getFarmersByProduct(product);
        return ResponseEntity.ok(farmers);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Farmer>> getFarmersByStatus(@PathVariable com.agrifood.platform.farmer.entity.FarmerStatus status) {
        List<Farmer> farmers = farmerService.getFarmersByStatus(status);
        return ResponseEntity.ok(farmers);
    }
}
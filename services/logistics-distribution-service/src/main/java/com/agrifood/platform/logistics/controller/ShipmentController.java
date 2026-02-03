package com.agrifood.platform.logistics.controller;

import com.agrifood.platform.logistics.entity.Shipment;
import com.agrifood.platform.logistics.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logistics")
public class ShipmentController {
    
    @Autowired
    private ShipmentService shipmentService;
    
    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments() {
        List<Shipment> shipments = shipmentService.getAllShipments();
        return ResponseEntity.ok(shipments);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable Long id) {
        return shipmentService.getShipmentById(id)
                .map(shipment -> ResponseEntity.ok(shipment))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Shipment> createShipment(@RequestBody Shipment shipment) {
        try {
            Shipment createdShipment = shipmentService.createShipment(shipment);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdShipment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Shipment> updateShipment(@PathVariable Long id, @RequestBody Shipment shipmentDetails) {
        try {
            Shipment updatedShipment = shipmentService.updateShipment(id, shipmentDetails);
            return ResponseEntity.ok(updatedShipment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {
        try {
            shipmentService.deleteShipment(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{id}/schedule")
    public ResponseEntity<Shipment> scheduleShipment(@PathVariable Long id) {
        try {
            Shipment updatedShipment = shipmentService.scheduleShipment(id);
            return ResponseEntity.ok(updatedShipment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PatchMapping("/{id}/deliver")
    public ResponseEntity<Shipment> markAsDelivered(@PathVariable Long id) {
        try {
            Shipment updatedShipment = shipmentService.markAsDelivered(id);
            return ResponseEntity.ok(updatedShipment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Shipment>> getShipmentsByStatus(@PathVariable com.agrifood.platform.logistics.entity.ShipmentStatus status) {
        List<Shipment> shipments = shipmentService.getShipmentsByStatus(status);
        return ResponseEntity.ok(shipments);
    }
    
    @GetMapping("/sender/{senderId}")
    public ResponseEntity<List<Shipment>> getShipmentsBySender(@PathVariable Long senderId) {
        List<Shipment> shipments = shipmentService.getShipmentsBySender(senderId);
        return ResponseEntity.ok(shipments);
    }
    
    @GetMapping("/receiver/{receiverId}")
    public ResponseEntity<List<Shipment>> getShipmentsByReceiver(@PathVariable Long receiverId) {
        List<Shipment> shipments = shipmentService.getShipmentsByReceiver(receiverId);
        return ResponseEntity.ok(shipments);
    }
}
package com.agrifood.platform.logistics.service;

import com.agrifood.platform.logistics.entity.Shipment;
import com.agrifood.platform.logistics.entity.TrackingEvent;
import com.agrifood.platform.logistics.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentService {
    
    @Autowired
    private ShipmentRepository shipmentRepository;
    
    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }
    
    public Optional<Shipment> getShipmentById(Long id) {
        return shipmentRepository.findById(id);
    }
    
    public Shipment createShipment(Shipment shipment) {
        // Validate shipment data
        validateShipment(shipment);
        
        // Set initial status
        shipment.setStatus(com.agrifood.platform.logistics.enums.ShipmentStatus.PENDING);
        
        return shipmentRepository.save(shipment);
    }
    
    public Shipment updateShipment(Long id, Shipment shipmentDetails) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id));
        
        // Update fields
        shipment.setOrigin(shipmentDetails.getOrigin());
        shipment.setDestination(shipmentDetails.getDestination());
        shipment.setItemIds(shipmentDetails.getItemIds());
        shipment.setTotalItems(shipmentDetails.getTotalItems());
        shipment.setWeight(shipmentDetails.getWeight());
        shipment.setVolume(shipmentDetails.getVolume());
        shipment.setTransportMode(shipmentDetails.getTransportMode());
        shipment.setScheduledPickup(shipmentDetails.getScheduledPickup());
        shipment.setScheduledDelivery(shipmentDetails.getScheduledDelivery());
        shipment.setCarrier(shipmentDetails.getCarrier());
        shipment.setShippingCost(shipmentDetails.getShippingCost());
        shipment.setSpecialInstructions(shipmentDetails.getSpecialInstructions());
        shipment.setPriority(shipmentDetails.getPriority());
        shipment.setSender(shipmentDetails.getSender());
        shipment.setReceiver(shipmentDetails.getReceiver());
        shipment.setUpdatedAt(java.time.LocalDateTime.now());
        
        return shipmentRepository.save(shipment);
    }
    
    public void deleteShipment(Long id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id));
        shipmentRepository.delete(shipment);
    }
    
    public Shipment scheduleShipment(Long id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id));
        
        if (shipment.getStatus() != com.agrifood.platform.logistics.entity.ShipmentStatus.PENDING) {
            throw new RuntimeException("Shipment must be in pending status to schedule");
        }
        
        shipment.setStatus(com.agrifood.platform.logistics.entity.ShipmentStatus.PICKED_UP);
        shipment.setActualPickup(java.time.LocalDateTime.now());
        shipment.setUpdatedAt(java.time.LocalDateTime.now());
        
        return shipmentRepository.save(shipment);
    }
    
    public Shipment markAsDelivered(Long id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id));
        
        if (shipment.getStatus() != com.agrifood.platform.logistics.entity.ShipmentStatus.OUT_FOR_DELIVERY) {
            throw new RuntimeException("Shipment must be out for delivery to mark as delivered");
        }
        
        shipment.setStatus(com.agrifood.platform.logistics.entity.ShipmentStatus.DELIVERED);
        shipment.setActualDelivery(java.time.LocalDateTime.now());
        shipment.setUpdatedAt(java.time.LocalDateTime.now());
        
        return shipmentRepository.save(shipment);
    }
    
    public Shipment addTrackingEvent(Long id, TrackingEvent trackingEvent) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id));
        
        // Add tracking event to shipment
        shipment.getTrackingEvents().add(trackingEvent);
        trackingEvent.setShipment(shipment);
        
        return shipmentRepository.save(shipment);
    }
    
    public List<Shipment> getShipmentsByStatus(com.agrifood.platform.logistics.entity.ShipmentStatus status) {
        return shipmentRepository.findByStatus(status);
    }
    
    public List<Shipment> getShipmentsBySender(Long senderId) {
        return shipmentRepository.findBySenderId(senderId);
    }
    
    public List<Shipment> getShipmentsByReceiver(Long receiverId) {
        return shipmentRepository.findByReceiverId(receiverId);
    }
    
    private void validateShipment(Shipment shipment) {
        if (shipment.getOrigin() == null || shipment.getOrigin().trim().isEmpty()) {
            throw new RuntimeException("Origin is required");
        }
        if (shipment.getDestination() == null || shipment.getDestination().trim().isEmpty()) {
            throw new RuntimeException("Destination is required");
        }
        if (shipment.getItemIds() == null || shipment.getItemIds().isEmpty()) {
            throw new RuntimeException("At least one item is required");
        }
        if (shipment.getTotalItems() == null || shipment.getTotalItems() <= 0) {
            throw new RuntimeException("Valid total items count is required");
        }
        if (shipment.getWeight() == null || shipment.getWeight().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valid weight is required");
        }
        if (shipment.getVolume() == null || shipment.getVolume().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valid volume is required");
        }
        if (shipment.getTransportMode() == null) {
            throw new RuntimeException("Transport mode is required");
        }
        if (shipment.getScheduledPickup() == null) {
            throw new RuntimeException("Scheduled pickup is required");
        }
        if (shipment.getScheduledDelivery() == null) {
            throw new RuntimeException("Scheduled delivery is required");
        }
        if (shipment.getSender() == null) {
            throw new RuntimeException("Sender is required");
        }
        if (shipment.getReceiver() == null) {
            throw new RuntimeException("Receiver is required");
        }
        if (shipment.getScheduledPickup().isAfter(shipment.getScheduledDelivery())) {
            throw new RuntimeException("Scheduled pickup cannot be after scheduled delivery");
        }
    }
}
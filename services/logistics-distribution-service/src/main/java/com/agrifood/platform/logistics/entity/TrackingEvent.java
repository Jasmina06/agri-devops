package com.agrifood.platform.logistics.entity;

import com.agrifood.platform.logistics.enums.TrackingEventType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tracking_events")
public class TrackingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;
    
    @Enumerated(EnumType.STRING)
    private TrackingEventType eventType;
    
    private String location;
    
    private String description;
    
    private LocalDateTime eventTime;
    
    // Constructors
    public TrackingEvent() {}
    
    public TrackingEvent(Shipment shipment, TrackingEventType eventType, String location, String description) {
        this.shipment = shipment;
        this.eventType = eventType;
        this.location = location;
        this.description = description;
        this.eventTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Shipment getShipment() {
        return shipment;
    }
    
    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }
    
    public TrackingEventType getEventType() {
        return eventType;
    }
    
    public void setEventType(TrackingEventType eventType) {
        this.eventType = eventType;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getEventTime() {
        return eventTime;
    }
    
    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }
}
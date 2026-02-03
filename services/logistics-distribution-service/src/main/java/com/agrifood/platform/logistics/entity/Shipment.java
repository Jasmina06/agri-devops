package com.agrifood.platform.logistics.entity;

import com.agrifood.platform.logistics.enums.TransportMode;
import com.agrifood.platform.logistics.enums.ShipmentStatus;
import com.agrifood.platform.logistics.enums.DeliveryPriority;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String shipmentNumber;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String destination;

    @ElementCollection
    @CollectionTable(name = "shipment_items", joinColumns = @JoinColumn(name = "shipment_id"))
    @Column(name = "item_id")
    private List<Long> itemIds;

    private Integer totalItems;

    private BigDecimal weight; // in kg

    private BigDecimal volume; // in cubic meters

    @Enumerated(EnumType.STRING)
    private TransportMode transportMode;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    private LocalDateTime scheduledPickup;

    private LocalDateTime scheduledDelivery;

    private LocalDateTime actualPickup;

    private LocalDateTime actualDelivery;

    private String carrier;

    private String trackingNumber;

    private BigDecimal shippingCost;

    @Column(columnDefinition = "TEXT")
    private String specialInstructions;

    @Enumerated(EnumType.STRING)
    private DeliveryPriority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private com.agrifood.platform.farmer.entity.Farmer sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private com.agrifood.platform.supplier.entity.Supplier receiver;

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL)
    private List<TrackingEvent> trackingEvents;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Constructors
    public Shipment() {}

    public Shipment(String origin, String destination, List<Long> itemIds, Integer totalItems,
                   BigDecimal weight, BigDecimal volume, TransportMode transportMode,
                   LocalDateTime scheduledPickup, LocalDateTime scheduledDelivery,
                   com.agrifood.platform.farmer.entity.Farmer sender,
                   com.agrifood.platform.supplier.entity.Supplier receiver) {
        this.shipmentNumber = generateShipmentNumber();
        this.origin = origin;
        this.destination = destination;
        this.itemIds = itemIds;
        this.totalItems = totalItems;
        this.weight = weight;
        this.volume = volume;
        this.transportMode = transportMode;
        this.scheduledPickup = scheduledPickup;
        this.scheduledDelivery = scheduledDelivery;
        this.sender = sender;
        this.receiver = receiver;
        this.status = ShipmentStatus.PENDING;
        this.priority = DeliveryPriority.NORMAL;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    private String generateShipmentNumber() {
        // Generate a unique shipment number
        return "SHIP-" + System.currentTimeMillis();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<Long> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Long> itemIds) {
        this.itemIds = itemIds;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public TransportMode getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(TransportMode transportMode) {
        this.transportMode = transportMode;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    public LocalDateTime getScheduledPickup() {
        return scheduledPickup;
    }

    public void setScheduledPickup(LocalDateTime scheduledPickup) {
        this.scheduledPickup = scheduledPickup;
    }

    public LocalDateTime getScheduledDelivery() {
        return scheduledDelivery;
    }

    public void setScheduledDelivery(LocalDateTime scheduledDelivery) {
        this.scheduledDelivery = scheduledDelivery;
    }

    public LocalDateTime getActualPickup() {
        return actualPickup;
    }

    public void setActualPickup(LocalDateTime actualPickup) {
        this.actualPickup = actualPickup;
    }

    public LocalDateTime getActualDelivery() {
        return actualDelivery;
    }

    public void setActualDelivery(LocalDateTime actualDelivery) {
        this.actualDelivery = actualDelivery;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    public DeliveryPriority getPriority() {
        return priority;
    }

    public void setPriority(DeliveryPriority priority) {
        this.priority = priority;
    }

    public com.agrifood.platform.farmer.entity.Farmer getSender() {
        return sender;
    }

    public void setSender(com.agrifood.platform.farmer.entity.Farmer sender) {
        this.sender = sender;
    }

    public com.agrifood.platform.supplier.entity.Supplier getReceiver() {
        return receiver;
    }

    public void setReceiver(com.agrifood.platform.supplier.entity.Supplier receiver) {
        this.receiver = receiver;
    }

    public List<TrackingEvent> getTrackingEvents() {
        return trackingEvents;
    }

    public void setTrackingEvents(List<TrackingEvent> trackingEvents) {
        this.trackingEvents = trackingEvents;
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
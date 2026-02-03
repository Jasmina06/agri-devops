package com.agrifood.platform.logistics.repository;

import com.agrifood.platform.logistics.entity.Shipment;
import com.agrifood.platform.logistics.enums.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findByStatus(ShipmentStatus status);
    List<Shipment> findBySenderId(Long senderId);
    List<Shipment> findByReceiverId(Long receiverId);
}
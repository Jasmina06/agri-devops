package com.agrifood.platform.supplier.repository;

import com.agrifood.platform.supplier.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByEmail(String email);
    List<Supplier> findByRegion(String region);
    List<Supplier> findByProductsContaining(String product);
    List<Supplier> findByStatus(com.agrifood.platform.supplier.enums.SupplierStatus status);
    List<Supplier> findByIsActiveTrue();
}
package com.agrifood.platform.farmer.repository;

import com.agrifood.platform.farmer.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long> {
    Optional<Farmer> findByEmail(String email);
    List<Farmer> findByRegion(String region);
    List<Farmer> findByProductsContaining(String product);
    List<Farmer> findByStatus(com.agrifood.platform.farmer.enums.FarmerStatus status);
}
package com.agrifood.platform.payment.repository;

import com.agrifood.platform.payment.entity.Invoice;
import com.agrifood.platform.payment.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByStatus(InvoiceStatus status);
    List<Invoice> findByCustomerId(Long customerId);
    List<Invoice> findBySupplierId(Long supplierId);
}
package com.agrifood.platform.payment.repository;

import com.agrifood.platform.payment.entity.Transaction;
import com.agrifood.platform.payment.enums.TransactionStatus;
import com.agrifood.platform.payment.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByTransactionId(String transactionId);
    List<Transaction> findByStatus(TransactionStatus status);
    List<Transaction> findByType(TransactionType type);
    List<Transaction> findByPayerId(Long payerId);
    List<Transaction> findByPayeeId(Long payeeId);
}
package com.agrifood.platform.payment.service;

import com.agrifood.platform.payment.entity.Transaction;
import com.agrifood.platform.payment.entity.Invoice;
import com.agrifood.platform.payment.repository.TransactionRepository;
import com.agrifood.platform.payment.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private InvoiceRepository invoiceRepository;
    
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }
    
    public Optional<Transaction> getTransactionByTransactionId(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId);
    }
    
    public Transaction createTransaction(Transaction transaction) {
        // Validate transaction data
        validateTransaction(transaction);
        
        // Set initial status
        transaction.setStatus(com.agrifood.platform.payment.enums.TransactionStatus.PENDING);
        
        // Process payment based on payment method
        processPayment(transaction);
        
        return transactionRepository.save(transaction);
    }
    
    public Transaction updateTransaction(Long id, Transaction transactionDetails) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
        
        // Update fields
        transaction.setAmount(transactionDetails.getAmount());
        transaction.setCurrency(transactionDetails.getCurrency());
        transaction.setType(transactionDetails.getType());
        transaction.setPaymentMethod(transactionDetails.getPaymentMethod());
        transaction.setPaymentMethodDetails(transactionDetails.getPaymentMethodDetails());
        transaction.setDescription(transactionDetails.getDescription());
        transaction.setPayer(transactionDetails.getPayer());
        transaction.setPayee(transactionDetails.getPayee());
        transaction.setNotes(transactionDetails.getNotes());
        transaction.setUpdatedAt(java.time.LocalDateTime.now());
        
        return transactionRepository.save(transaction);
    }
    
    public void deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
        transactionRepository.delete(transaction);
    }
    
    public Transaction processPayment(Transaction transaction) {
        // Simulate payment processing
        // In a real system, this would integrate with payment gateways
        try {
            // Simulate payment processing delay
            Thread.sleep(1000); // 1 second delay
            
            // Update transaction status based on payment result
            transaction.setStatus(com.agrifood.platform.payment.entity.TransactionStatus.PROCESSING);
            transaction.setProcessedAt(java.time.LocalDateTime.now());
            
            // Simulate successful payment
            transaction.setStatus(com.agrifood.platform.payment.entity.TransactionStatus.COMPLETED);
            transaction.setCompletedAt(java.time.LocalDateTime.now());
            
            // Generate invoice for completed payment
            if (transaction.getType() == com.agrifood.platform.payment.entity.TransactionType.PAYMENT) {
                createInvoiceForTransaction(transaction);
            }
            
        } catch (Exception e) {
            transaction.setStatus(com.agrifood.platform.payment.entity.TransactionStatus.FAILED);
            transaction.setGatewayResponse("Payment failed: " + e.getMessage());
        }
        
        return transactionRepository.save(transaction);
    }
    
    public Transaction refundTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
        
        if (transaction.getStatus() != com.agrifood.platform.payment.entity.TransactionStatus.COMPLETED) {
            throw new RuntimeException("Only completed transactions can be refunded");
        }
        
        // Create refund transaction
        Transaction refundTransaction = new Transaction();
        refundTransaction.setOrderId(transaction.getOrderId());
        refundTransaction.setAmount(transaction.getAmount().negate()); // Negative amount for refund
        refundTransaction.setCurrency(transaction.getCurrency());
        refundTransaction.setType(com.agrifood.platform.payment.entity.TransactionType.REFUND);
        refundTransaction.setPayer(transaction.getPayee()); // Payee becomes payer for refund
        refundTransaction.setPayee(transaction.getPayer()); // Payer becomes payee for refund
        refundTransaction.setDescription("Refund for transaction: " + transaction.getTransactionId());
        refundTransaction.setStatus(com.agrifood.platform.payment.entity.TransactionStatus.PENDING);
        
        refundTransaction = transactionRepository.save(refundTransaction);
        
        // Process the refund
        processPayment(refundTransaction);
        
        return refundTransaction;
    }
    
    public List<Transaction> getTransactionsByStatus(com.agrifood.platform.payment.entity.TransactionStatus status) {
        return transactionRepository.findByStatus(status);
    }
    
    public List<Transaction> getTransactionsByType(com.agrifood.platform.payment.entity.TransactionType type) {
        return transactionRepository.findByType(type);
    }
    
    public List<Transaction> getTransactionsByPayer(Long payerId) {
        return transactionRepository.findByPayerId(payerId);
    }
    
    public List<Transaction> getTransactionsByPayee(Long payeeId) {
        return transactionRepository.findByPayeeId(payeeId);
    }
    
    public Invoice createInvoiceForTransaction(Transaction transaction) {
        // Create an invoice based on the transaction
        Invoice invoice = new Invoice(
            transaction.getOrderId(),
            transaction.getPayee(),
            transaction.getPayer(),
            transaction.getAmount(),
            transaction.getCurrency(),
            java.time.LocalDateTime.now().plusDays(30) // Due in 30 days
        );
        
        invoice.setNotes("Invoice for transaction: " + transaction.getTransactionId());
        invoice.setStatus(com.agrifood.platform.payment.entity.InvoiceStatus.SENT);
        
        return invoiceRepository.save(invoice);
    }
    
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }
    
    public Invoice updateInvoiceStatus(Long id, com.agrifood.platform.payment.entity.InvoiceStatus status) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
        
        invoice.setStatus(status);
        invoice.setUpdatedAt(java.time.LocalDateTime.now());
        
        if (status == com.agrifood.platform.payment.entity.InvoiceStatus.PAID) {
            invoice.setPaidDate(java.time.LocalDateTime.now());
        }
        
        return invoiceRepository.save(invoice);
    }
    
    private void validateTransaction(Transaction transaction) {
        if (transaction.getOrderId() == null || transaction.getOrderId().trim().isEmpty()) {
            throw new RuntimeException("Order ID is required");
        }
        if (transaction.getAmount() == null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valid amount is required");
        }
        if (transaction.getCurrency() == null || transaction.getCurrency().trim().isEmpty()) {
            throw new RuntimeException("Currency is required");
        }
        if (transaction.getType() == null) {
            throw new RuntimeException("Transaction type is required");
        }
        if (transaction.getPaymentMethod() == null) {
            throw new RuntimeException("Payment method is required");
        }
        if (transaction.getPayer() == null) {
            throw new RuntimeException("Payer is required");
        }
        if (transaction.getPayee() == null) {
            throw new RuntimeException("Payee is required");
        }
    }
}
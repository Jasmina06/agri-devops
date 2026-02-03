package com.agrifood.platform.payment.controller;

import com.agrifood.platform.payment.entity.Transaction;
import com.agrifood.platform.payment.entity.Invoice;
import com.agrifood.platform.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;
    
    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = paymentService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return paymentService.getTransactionById(id)
                .map(transaction -> ResponseEntity.ok(transaction))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/transactions")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        try {
            Transaction createdTransaction = paymentService.createTransaction(transaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/transactions/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody Transaction transactionDetails) {
        try {
            Transaction updatedTransaction = paymentService.updateTransaction(id, transactionDetails);
            return ResponseEntity.ok(updatedTransaction);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        try {
            paymentService.deleteTransaction(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/transactions/{id}/process")
    public ResponseEntity<Transaction> processPayment(@PathVariable Long id) {
        Transaction transaction = paymentService.getTransactionById(id).orElse(null);
        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }
        
        try {
            Transaction processedTransaction = paymentService.processPayment(transaction);
            return ResponseEntity.ok(processedTransaction);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/transactions/{id}/refund")
    public ResponseEntity<Transaction> refundTransaction(@PathVariable Long id) {
        try {
            Transaction refundTransaction = paymentService.refundTransaction(id);
            return ResponseEntity.ok(refundTransaction);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/transactions/status/{status}")
    public ResponseEntity<List<Transaction>> getTransactionsByStatus(@PathVariable com.agrifood.platform.payment.entity.TransactionStatus status) {
        List<Transaction> transactions = paymentService.getTransactionsByStatus(status);
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/transactions/type/{type}")
    public ResponseEntity<List<Transaction>> getTransactionsByType(@PathVariable com.agrifood.platform.payment.entity.TransactionType type) {
        List<Transaction> transactions = paymentService.getTransactionsByType(type);
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/invoices/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        Invoice invoice = paymentService.getInvoiceById(id);
        if (invoice == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invoice);
    }
    
    @PatchMapping("/invoices/{id}/status/{status}")
    public ResponseEntity<Invoice> updateInvoiceStatus(@PathVariable Long id, @PathVariable com.agrifood.platform.payment.entity.InvoiceStatus status) {
        try {
            Invoice updatedInvoice = paymentService.updateInvoiceStatus(id, status);
            return ResponseEntity.ok(updatedInvoice);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
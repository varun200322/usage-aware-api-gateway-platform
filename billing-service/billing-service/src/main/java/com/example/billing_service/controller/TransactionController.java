package com.example.billing_service.controller;

import com.example.billing_service.dto.TransactionRequest;
import com.example.billing_service.dto.TransactionSummaryResponse;
import com.example.billing_service.model.Transaction;
import com.example.billing_service.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction createTransaction(@Valid @RequestBody TransactionRequest request) {
        return transactionService.createTransaction(request);
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/summary")
    public TransactionSummaryResponse getSummary() {
        return transactionService.getSummary();
    }
}

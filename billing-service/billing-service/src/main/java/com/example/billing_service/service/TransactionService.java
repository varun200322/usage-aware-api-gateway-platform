package com.example.billing_service.service;

import com.example.billing_service.dto.TransactionRequest;
import com.example.billing_service.dto.TransactionSummaryResponse;
import com.example.billing_service.model.Transaction;
import com.example.billing_service.model.TransactionType;
import com.example.billing_service.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(TransactionRequest request) {
        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .amount(request.getAmount())
                .type(request.getType())
                .category(request.getCategory())
                .timestamp(Instant.now())
                .build();

        transactionRepository.save(transaction);
        return transaction;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public TransactionSummaryResponse getSummary() {
        List<Transaction> transactions = transactionRepository.findAll();

        double totalIncome = transactions.stream()
                .filter(transaction -> transaction.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpense = transactions.stream()
                .filter(transaction -> transaction.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double balance = totalIncome - totalExpense;

        return TransactionSummaryResponse.builder()
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .balance(balance)
                .build();
    }
}
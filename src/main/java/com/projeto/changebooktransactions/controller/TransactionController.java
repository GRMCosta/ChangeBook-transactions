package com.projeto.changebooktransactions.controller;

import com.projeto.changebooktransactions.domain.TransactionRequest;
import com.projeto.changebooktransactions.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/change-book/v1/transaction")
@CrossOrigin
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createTransaction(@RequestBody TransactionRequest transactionRequest) throws IllegalArgumentException {
        if (transactionRequest != null)
            transactionService.saveTransaction(transactionRequest);
        else
            throw new IllegalArgumentException("Invalid data.");
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateTransaction(@RequestBody TransactionRequest transactionRequest) throws IllegalArgumentException {
        if (transactionRequest != null)
            transactionService.updateTransaction(transactionRequest);
        else
            throw new IllegalArgumentException("Invalid data.");
    }
}

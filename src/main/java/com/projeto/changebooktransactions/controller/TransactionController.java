package com.projeto.changebooktransactions.controller;

import com.projeto.changebooktransactions.domain.Transaction;
import com.projeto.changebooktransactions.domain.TransactionRequest;
import com.projeto.changebooktransactions.integration.book.client.BookClient;
import com.projeto.changebooktransactions.integration.user.client.UserClient;
import com.projeto.changebooktransactions.service.TransactionService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/change-book/v1/transactions")
@CrossOrigin
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    private BookClient bookClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createTransaction(@RequestBody @Valid TransactionRequest transactionRequest,
                                  @RequestHeader String Authorization) throws IllegalArgumentException {
        val user = userClient.getUserByToken(Authorization);
        val bookPartner = bookClient.getBookById(transactionRequest.getBookPartnerId(), Authorization);
        if(!(transactionRequest.getBookUserId() == null)){
            val bookUser = bookClient.getBookById(transactionRequest.getBookUserId(), Authorization);
            val transaction = transactionRequest.toTradeTransaction(bookPartner, bookUser);
            transactionService.createTransaction(transaction);
        } else{
            val transaction = transactionRequest.toSellTransaction(bookPartner, user);
            transactionService.createTransaction(transaction);
        }

    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateTransaction(@RequestBody Transaction transaction) throws IllegalArgumentException {
        if (transaction != null)
            transactionService.updateTransaction(transaction);
        else
            throw new IllegalArgumentException("Invalid data.");
    }
}

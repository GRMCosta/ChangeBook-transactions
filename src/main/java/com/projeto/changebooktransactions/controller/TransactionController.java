package com.projeto.changebooktransactions.controller;

import com.projeto.changebooktransactions.domain.Transaction;
import com.projeto.changebooktransactions.domain.TransactionRequest;
import com.projeto.changebooktransactions.facade.TransactionFacade;
import com.projeto.changebooktransactions.integration.book.client.BookClient;
import com.projeto.changebooktransactions.integration.user.client.UserClient;
import com.projeto.changebooktransactions.service.TransactionService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/change-book/v1/transactions")
@CrossOrigin
public class TransactionController {

    private TransactionFacade transactionFacade;


    @Autowired
    public TransactionController(TransactionFacade transactionFacade) {
        this.transactionFacade = transactionFacade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createTransaction(@RequestBody @Valid TransactionRequest transactionRequest,
                                  @RequestHeader String Authorization) throws IllegalArgumentException {
        transactionFacade.createTransaction(transactionRequest, Authorization);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateTransaction(@RequestBody Transaction transaction) throws IllegalArgumentException {
        if (transaction != null)
            transactionFacade.updateTransaction(transaction);
        else
            throw new IllegalArgumentException("Invalid data.");
    }

    @GetMapping("/incompleted")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getUserIncompletedTransactions(@RequestHeader String Authorization){
        return ResponseEntity.ok(transactionFacade.getUserIncompleteTransactons(Authorization));
    }

    @GetMapping("/completed")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getUserCompletedTransactions(@RequestHeader String Authorization){
        return ResponseEntity.ok(transactionFacade.getUserCompleteTransactons(Authorization));
    }
}

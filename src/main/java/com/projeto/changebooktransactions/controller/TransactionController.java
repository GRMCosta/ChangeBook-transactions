package com.projeto.changebooktransactions.controller;

import com.projeto.changebooktransactions.domain.Transaction;
import com.projeto.changebooktransactions.domain.TransactionRequest;
import com.projeto.changebooktransactions.facade.TransactionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateTransaction(@RequestParam String transactionId,@RequestHeader String Authorization) throws IllegalArgumentException {
        if (transactionId != null)
            transactionFacade.updateTransaction(transactionId);
        else
            throw new IllegalArgumentException("Invalid data.");
    }

    @GetMapping("/incompleted")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getUserIncompletedTransactions(@RequestParam @NotNull String expand, @RequestHeader String Authorization){
        return ResponseEntity.ok(transactionFacade.getUserIncompleteTransactions(expand,Authorization));
    }

    @GetMapping("/completed")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getUserCompletedTransactions(@RequestParam @NotNull String expand,@RequestHeader String Authorization){
        return ResponseEntity.ok(transactionFacade.getUserCompleteTransactions(expand,Authorization));
    }

    @PatchMapping("/cancel")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void cancelTransactions(@RequestParam String transactionId,@RequestHeader String Authorization) {
        transactionFacade.cancelTransaction(transactionId, Authorization);
    }
}

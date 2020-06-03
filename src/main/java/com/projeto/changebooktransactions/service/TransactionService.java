package com.projeto.changebooktransactions.service;

import com.projeto.changebooktransactions.config.Messages;
import com.projeto.changebooktransactions.config.exception.TransactionException;
import com.projeto.changebooktransactions.domain.Transaction;
import com.projeto.changebooktransactions.domain.TransactionType;
import com.projeto.changebooktransactions.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void createTransaction(Transaction transaction){
        if(transaction.getTransactionType().equals(TransactionType.SELL) &&
        transaction.getPrice() == null)
            throw new TransactionException(Messages.PRICE_IS_REQUIRED);
        transactionRepository.save(transaction);

    }
    public void updateTransaction(Transaction transaction){
        if (transaction != null && transactionRepository.existsById(transaction.getId()))
            transactionRepository.save(transaction);
        else
            throw new IllegalArgumentException("Transaction not exists.");
    }


}

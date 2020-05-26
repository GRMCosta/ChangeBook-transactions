package com.projeto.changebooktransactions.service;

import com.projeto.changebooktransactions.domain.TransactionRequest;
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

    public void saveTransaction(TransactionRequest transactionRequest){
        if (transactionRequest != null)
            transactionRepository.save(transactionRequest);
        else
            throw new IllegalArgumentException();
    }
    public void updateTransaction(TransactionRequest transactionRequest){
        if (transactionRequest != null && transactionRepository.existsById(transactionRequest.getId()))
            transactionRepository.save(transactionRequest);
        else
            throw new IllegalArgumentException("Tranaction not exists.");
    }


}

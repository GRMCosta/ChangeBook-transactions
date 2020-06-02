package com.projeto.changebooktransactions.service;

import com.projeto.changebooktransactions.domain.TransactionRequest;
import com.projeto.changebooktransactions.domain.TransactionType;
import com.projeto.changebooktransactions.integration.book.client.BookClient;
import com.projeto.changebooktransactions.integration.user.response.User;
import com.projeto.changebooktransactions.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    protected BookClient bookClient;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void saveTransaction(TransactionRequest transactionRequest){
        if (transactionRequest != null && transactionRequest.getTransactionType() != null) {
            transactionRequest.setEndDate(LocalDate.now());
            updateBookInformations(transactionRequest);
            transactionRepository.save(transactionRequest);
        }
        else
            throw new IllegalArgumentException();
    }

    public void updateTransaction(TransactionRequest transactionRequest){
        if (transactionRequest != null && transactionRepository.existsById(transactionRequest.getId())
                && transactionRequest.getTransactionType() != null) {
            validateTransactionType(transactionRequest.getTransactionType());
            transactionRequest.setEndDate(LocalDate.now());
            updateBookInformations(transactionRequest);
            transactionRepository.save(transactionRequest);
        }
        else
            throw new IllegalArgumentException("Transaction not exists.");
    }

    private void validateTransactionType(String transactionType) {
        if (!transactionType.equals(TransactionType.SELL.toString()) ||
                !transactionType.equals(TransactionType.TRADE.toString()));
            throw new IllegalArgumentException();
    }

    private void updateBookInformations(TransactionRequest request){
        if (request.isComplete() && request.getTransactionType().equals(TransactionType.TRADE)){
            User newUser = request.getNewOwer().getUser();
            request.getNewOwer().setUser(request.getOldOwer().getUser());
            bookClient.updateBook(request.getNewOwer());
            request.getOldOwer().setUser(newUser);
            bookClient.updateBook(request.getOldOwer());
        }
        else if (request.isComplete() && request.getTransactionType().equals(TransactionType.SELL)){
            request.getNewOwer().setUser(request.getOldOwer().getUser());
            bookClient.updateBook(request.getNewOwer());
        }
    }


}

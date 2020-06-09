package com.projeto.changebooktransactions.facade;

import com.projeto.changebooktransactions.domain.StatusTransaction;
import com.projeto.changebooktransactions.domain.Transaction;
import com.projeto.changebooktransactions.domain.TransactionRequest;
import com.projeto.changebooktransactions.integration.book.client.BookClient;
import com.projeto.changebooktransactions.integration.user.client.UserClient;
import com.projeto.changebooktransactions.integration.user.response.User;
import com.projeto.changebooktransactions.service.TransactionService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionFacade {
    private TransactionService transactionService;

    @Autowired
    private BookClient bookClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    public TransactionFacade(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void createTransaction(TransactionRequest transactionRequest,
                                  String Authorization) throws IllegalArgumentException {
        val user = getUserByToken(Authorization);
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

    public List<Transaction> getUserIncompleteTransactions(String Authorization){
        return getTransactions(Authorization).stream()
                .filter(t -> t.getStatusTransaction() == StatusTransaction.PENDING).collect(Collectors.toList());
    }

    public List<Transaction> getUserCompleteTransactions(String Authorization){
        return getTransactions(Authorization).stream().
                filter(t -> t.getStatusTransaction() != StatusTransaction.PENDING).collect(Collectors.toList());
    }

    private List<Transaction> getTransactions(String Authorization) {
        final User user = userClient.getUserByToken(Authorization);

        if (!transactionService.existsByNewOwner(user))
            throw new InternalError("O usuário não possui transações ativas.");

        return transactionService.getUserTransaction(user);
    }

    public void updateTransaction(Transaction transaction) throws IllegalArgumentException {
        if (transaction != null)
            transactionService.updateTransaction(transaction);
        else
            throw new IllegalArgumentException("Invalid data.");
    }

    private User getUserByToken(String authorization){
        return userClient.getUserByToken(authorization);
    }

    public void cancelTransaction(String transactionId, String Authorization) {
        final User user = userClient.getUserByToken(Authorization);
        transactionService.cancelTransactionById(user, transactionId);
    }
}

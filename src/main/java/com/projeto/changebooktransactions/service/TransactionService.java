package com.projeto.changebooktransactions.service;

import com.projeto.changebooktransactions.config.Messages;
import com.projeto.changebooktransactions.config.exception.TransactionException;
import com.projeto.changebooktransactions.domain.StatusTransaction;
import com.projeto.changebooktransactions.domain.Transaction;
import com.projeto.changebooktransactions.domain.TransactionType;
import com.projeto.changebooktransactions.integration.book.client.BookClient;
import com.projeto.changebooktransactions.integration.user.response.User;
import com.projeto.changebooktransactions.repository.TransactionRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    protected BookClient bookClient;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getUserTransaction(User user) {
        return transactionRepository.getByNewOwner(user);
    }

    public Boolean existsByNewOwner(User user) {
        return transactionRepository.existsByNewOwner(user);
    }

    public void createTransaction(Transaction transaction) {
        if (transaction.getTransactionType().equals(TransactionType.SELL) &&
                transaction.getPrice() == null)
            throw new TransactionException(Messages.PRICE_IS_REQUIRED);
        transactionRepository.save(transaction);

    }


    public void updateTransaction(Transaction transaction) {
        if (transaction != null && transactionRepository.existsById(transaction.getId())
                && transaction.getTransactionType() != null) {
            validateTransactionType(transaction.getTransactionType());
            if (transaction.getStatusTransaction().equals(StatusTransaction.COMPLETED))
                transaction.setEndDate(LocalDate.now());
            updateBookInformation(transaction);
            transactionRepository.save(transaction);
        } else
            throw new IllegalArgumentException("Transaction not exists.");
    }

    private void validateTransactionType(TransactionType transactionType) {
        if (!transactionType.equals(TransactionType.SELL.toString())
                || !transactionType.equals(TransactionType.TRADE.toString())) ;
        throw new IllegalArgumentException();
    }

    private void updateBookInformation(Transaction transaction) {
        User newUser = transaction.getNewOwner();
        if (transaction.getStatusTransaction().equals(StatusTransaction.COMPLETED) && transaction.getTransactionType().equals(TransactionType.TRADE)) {
            transaction.getBookUser().setUser(transaction.getOldOwner());
            bookClient.updateBook(transaction.getBookUser());
            transaction.getBookPartner().setUser(newUser);
            bookClient.updateBook(transaction.getBookPartner());
        } else if (transaction.getStatusTransaction().equals(StatusTransaction.COMPLETED) && transaction.getTransactionType().equals(TransactionType.SELL)) {
            transaction.getBookPartner().setUser(newUser);
            bookClient.updateBook(transaction.getBookPartner());
        }

    }


    public void cancelTransactionById(User user,String transactionId) {
        val transaction = transactionRepository.findById(transactionId).get();
        if(!user.getCpf().equals(transaction.getOldOwner().getCpf())){
            throw new IllegalArgumentException("This user is not the owner");
        } else if(transaction.getStatusTransaction().equals(StatusTransaction.COMPLETED)){
            throw new IllegalArgumentException("This transaction is already completed");
        }
        transaction.setStatusTransaction(StatusTransaction.CANCEL);
        transactionRepository.save(transaction);
    }
}

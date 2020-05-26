package com.projeto.changebooktransactions.repository;

import com.projeto.changebooktransactions.domain.TransactionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionRequest, String> {
}

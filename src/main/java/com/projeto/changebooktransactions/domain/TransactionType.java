package com.projeto.changebooktransactions.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionType {
    TRADE(1), SELL(2);

    public Integer transactionId;
}

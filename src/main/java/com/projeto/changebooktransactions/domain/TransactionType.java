package com.projeto.changebooktransactions.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionType {
    TRADE(0), SELL(1);

    public Integer transactionId;
}

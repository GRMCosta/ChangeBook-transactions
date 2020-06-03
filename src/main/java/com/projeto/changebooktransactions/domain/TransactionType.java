package com.projeto.changebooktransactions.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum TransactionType {
    TRADE(0), SELL(1);

    public Integer transactionId;

    TransactionType(Integer transactionId){this.transactionId = transactionId;}
    public TransactionType getById(Integer transactionId) {
        if (transactionId != null) {
            for (TransactionType type : TransactionType.values()) {
                if (type.equals(transactionId))
                    return type;
                else
                    throw new IllegalArgumentException();
            }
        }
        throw new IllegalArgumentException();
    }
}

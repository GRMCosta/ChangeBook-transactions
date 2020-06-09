package com.projeto.changebooktransactions.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum StatusTransaction {
    PENDING(0) , CANCELED(1), COMPLETED(2);

    private Integer indicator;

    StatusTransaction(int indicator) {
        this.indicator = indicator;
    }
}

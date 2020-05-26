package com.projeto.changebooktransactions.domain;

import com.projeto.changebooktransactions.integration.book.Book;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TransactionRequest {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @OneToOne
    @JoinColumn(name = "old_ower")
    private Book oldOwer;

    @OneToOne
    @JoinColumn(name = "new_ower")
    private Book newOwer;

    private TransactionType transactionType;

    private BigDecimal price;

    private boolean isComplete;

    private LocalDate endDate;
}

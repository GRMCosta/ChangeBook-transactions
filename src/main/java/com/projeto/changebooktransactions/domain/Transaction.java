package com.projeto.changebooktransactions.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projeto.changebooktransactions.integration.book.response.Book;
import com.projeto.changebooktransactions.integration.user.response.User;
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
public class Transaction {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @OneToOne
    @JoinColumn(name = "book_partner")
    private Book bookPartner;

    @OneToOne
    @JoinColumn(name = "book_user")
    private Book bookUser;

    @OneToOne
    @JoinColumn(name = "old_owner")
    private User oldOwner;

    @OneToOne
    @JoinColumn(name = "new_owner")
    private User newOwner;

    @JsonProperty("transaction_type")
    private TransactionType transactionType;

    private BigDecimal price;

    @JsonProperty("status_transaction")
    private StatusTransaction statusTransaction;

    @JsonProperty("end_date")
    private LocalDate endDate;
}

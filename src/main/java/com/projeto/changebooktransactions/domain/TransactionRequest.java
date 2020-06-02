package com.projeto.changebooktransactions.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.projeto.changebooktransactions.integration.book.Book;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transaction")
public class TransactionRequest {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @OneToOne
    @NotNull
    @JoinColumn(name = "old_ower")
    @JsonProperty("old_ower")
    private Book oldOwer;

    @OneToOne
    @NotNull
    @JoinColumn(name = "new_ower")
    @JsonProperty("new_ower")
    private Book newOwer;

    @JsonProperty("transaction_type")
    private String transactionType;

    private BigDecimal price;

    @JsonProperty("is_complete")
    private boolean isComplete;

    @JsonProperty("end_date")
    private LocalDate endDate;
}

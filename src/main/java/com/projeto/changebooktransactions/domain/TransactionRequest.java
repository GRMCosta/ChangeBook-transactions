package com.projeto.changebooktransactions.domain;

import com.projeto.changebooktransactions.config.Messages;
import com.projeto.changebooktransactions.integration.book.response.Book;
import com.projeto.changebooktransactions.integration.user.response.User;
import lombok.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {

    @NotBlank(message = Messages.BOOK_PARTNER_IS_REQUIRED)
    private String bookPartnerId;

    private String bookUserId;

    private BigDecimal price;

    private StatusTransaction statusTransaction;


    public Transaction toTradeTransaction(Book bookPartner, Book bookUser){
        return Transaction.builder()
                .bookPartner(bookPartner)
                .oldOwner(bookPartner.getUser())
                .bookUser(bookUser)
                .newOwner(bookUser.getUser())
                .transactionType(TransactionType.TRADE)
                .price(this.getPrice())
                .endDate(LocalDate.now())
                .statusTransaction(StatusTransaction.PENDING)
                .build();
    }

    public Transaction toSellTransaction(Book bookPartner, User user){
        return Transaction.builder()
                .bookPartner(bookPartner)
                .oldOwner(bookPartner.getUser())
                .newOwner(user)
                .transactionType(TransactionType.SELL)
                .price(this.getPrice())
                .endDate(LocalDate.now())
                .statusTransaction(StatusTransaction.PENDING)
                .build();
    }
}

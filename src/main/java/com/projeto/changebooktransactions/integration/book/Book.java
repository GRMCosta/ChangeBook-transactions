package com.projeto.changebooktransactions.integration.book;

import com.projeto.changebooktransactions.config.Messages;
import com.projeto.changebooktransactions.integration.user.response.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Book {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotBlank(message = Messages.NAME_IS_REQUIRED)
    private String name;

    @NotBlank(message = Messages.AUTHOR_NAME_IS_REQUIRED)
    private String authorName;

    @NotBlank(message = Messages.EDITOR_NAME_IS_REQUIRED)
    private String editorName;

    @NotBlank(message = Messages.DESCRIPTION_IS_REQUIRED)
    private String description;

    @NotBlank(message = Messages.CATEGORY_IS_REQUIRED)
    private String category;

    @NotNull(message = Messages.IS_FOR_TRADE_IS_REQUIRED)
    private Boolean isForTrade;

    @NotBlank(message = Messages.TRADE_DESCRIPTION_IS_REQUIRED)
    private String tradeDescription;

    @NotNull(message = Messages.IS_FOR_SELL_IS_REQUIRED)
    private Boolean isForSell;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

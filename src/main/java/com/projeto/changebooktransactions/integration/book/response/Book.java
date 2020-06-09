package com.projeto.changebooktransactions.integration.book.response;

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
    private String id;

    private String name;

    private String authorName;

    private String editorName;

    private String description;

    private String category;

    private Boolean isForTrade;

    private String tradeDescription;

    private Boolean isForSell;

    @NotNull(message = Messages.IMAGE_IS_REQUIRED)
    private String image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

package com.projeto.changebooktransactions.integration.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @JsonProperty("user_name")
    private String userName;

    @Id
    private String cpf;

    @Column(unique = true)
    private String city;

    @Column(unique = true)
    private String email;

    private String phone;

}

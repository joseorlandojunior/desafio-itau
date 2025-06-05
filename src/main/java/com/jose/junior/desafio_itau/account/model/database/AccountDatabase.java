package com.jose.junior.desafio_itau.account.model.database;

import com.jose.junior.desafio_itau.account.model.domain.Account;
import com.jose.junior.desafio_itau.person.model.database.PersonDatabase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "account")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Value
public class AccountDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    BigDecimal balance;

    Boolean active;

    @OneToOne
    @JoinColumn(name = "person_id")
    PersonDatabase client;

    public Account toDomain() {
        return Account.builder()
                .id(id)
                .active(active)
                .balance(balance)
                .client(client != null ? client.toDomain() : null)
                .build();
    }
}
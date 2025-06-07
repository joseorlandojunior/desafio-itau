package com.jose.junior.desafio_itau.account.model.database;

import com.jose.junior.desafio_itau.account.model.domain.Account;
import com.jose.junior.desafio_itau.person.model.database.PersonDatabase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@Entity(name = "account")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Value
public class AccountDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Long id;

    @Column
    BigDecimal balance;

    @Column
    Boolean active;

    @OneToOne
    @JoinColumn(name = "person_id")
    PersonDatabase client;

    public Account toDomain(boolean includeClient) {
        return Account.builder()
                .id(id)
                .active(active)
                .balance(balance)
                .client(includeClient && client != null ? client.toDomain(false) : null)
                .build();
    }
}
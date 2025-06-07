package com.jose.junior.desafio_itau.account.model.domain;

import com.jose.junior.desafio_itau.account.model.database.AccountDatabase;
import com.jose.junior.desafio_itau.person.model.domain.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Account {

    private Long id;

    private BigDecimal balance;

    private Person client;

    private Boolean active;

    public void disable() {
        this.active = false;
    }

    public void enable() {
        this.active = true;
    }

    public void addBalance(BigDecimal balanceForAdd) {
        this.balance = balance.add(balanceForAdd);
    }

    public void debitBalance(BigDecimal balanceForDebit) {
        this.balance = balance.subtract(balanceForDebit);
    }

    public AccountDatabase toDatabase(boolean includePerson) {
        return AccountDatabase.builder()
                .id(id)
                .balance(balance)
                .active(active)
                .client(includePerson && client != null ? client.toDatabase(false) : null)
                .build();
    }
}

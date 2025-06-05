package com.jose.junior.desafio_itau.account.model.domain;

import com.jose.junior.desafio_itau.account.model.database.AccountDatabase;
import com.jose.junior.desafio_itau.person.model.domain.Person;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    private final Person client;

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

    public AccountDatabase toDatabase() {
        return AccountDatabase.builder()
                .balance(balance)
                .active(active)
                .client(client.toDatabase())
                .build();
    }
}

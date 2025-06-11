package com.jose.junior.desafio_itau.account.useCase;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

public interface GetAccountUseCase {

    AccountDTO execute(String accountOwner);

    @Builder
    @Getter
    class AccountDTO {
        private final Long id;
        private final BigDecimal balance;
        private final String documentOwner;
        private final Boolean active;

    }
}

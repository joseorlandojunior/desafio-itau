package com.jose.junior.desafio_itau.account.useCase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface DebitBalanceUseCase {

    void execute(DebitBalanceCommand cmd);

    @Builder
    @Value
    @JsonIgnoreProperties(value = {"documentAccountOwner"})
    class DebitBalanceCommand {

        @NotNull(message = "Account id must not be null.")
        Long accountId;

        @NotNull(message = "value for debit must not be null.")
        BigDecimal valueForDebit;
        String documentAccountOwner;
        LocalDateTime time;

        public DebitBalanceCommand withOwner(String accountOwner) {
            return DebitBalanceCommand.builder()
                    .accountId(accountId)
                    .valueForDebit(valueForDebit)
                    .documentAccountOwner(accountOwner)
                    .time(time)
                    .build();
        }
    }
}

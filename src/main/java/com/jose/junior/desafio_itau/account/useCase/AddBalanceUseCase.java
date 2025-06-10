package com.jose.junior.desafio_itau.account.useCase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface AddBalanceUseCase {

    void execute(AddBalanceCommand cmd);

    @Builder
    @Getter
    @JsonIgnoreProperties(value = {"documentAccountOwner"})
    class AddBalanceCommand {

        @NotNull(message = "Account id must not be null.")
        private final Long accountId;

        @NotNull(message = "Value for add id must not be null.")
        private final BigDecimal valueForAdd;
        private final String documentAccountOwner;
        private final LocalDateTime time;

        public AddBalanceCommand withOwner(String accountOwner) {
            return AddBalanceCommand.builder()
                    .accountId(accountId)
                    .valueForAdd(valueForAdd)
                    .documentAccountOwner(accountOwner)
                    .time(time)
                    .build();
        }
    }
}

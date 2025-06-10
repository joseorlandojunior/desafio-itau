package com.jose.junior.desafio_itau.account.useCase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

public interface EnableAccountUseCase {

    void execute(EnableAccountCommand cmd);

    @Builder
    @Getter
    @JsonIgnoreProperties(value = {"managerDocument"})
    class EnableAccountCommand{
        private final String managerDocument;

        @NotNull(message = "Account id cannot be null.")
        private final Long accountId;

        public EnableAccountCommand withManagerDocument(String managerDocument) {
            return EnableAccountCommand.builder()
                    .managerDocument(managerDocument)
                    .accountId(accountId)
                    .build();
        }
    }
}

package com.jose.junior.desafio_itau.account.useCase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

public interface DisableAccountUseCase {

    void execute(DisableAccountCommand cmd);

    @Builder
    @Getter
    @JsonIgnoreProperties(value = {"managerDocument"})
    class DisableAccountCommand {
        private final String managerDocument;
        @NotNull(message = "Account id cannot be null.")
        private final Long accountId;

        public DisableAccountCommand withManagerDocument(String managerDocument) {
            return DisableAccountCommand.builder()
                    .managerDocument(managerDocument)
                    .accountId(accountId)
                    .build();
        }
    }
}

package com.jose.junior.desafio_itau.account.useCase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

public interface CreateAccountUseCase {

    void execute(CreateAccountCommand cmd);

    @Builder
    @Getter
    @JsonIgnoreProperties(value = {"managerDocument"})
    class CreateAccountCommand{
        private String managerDocument;
        private String personDocument;

        public CreateAccountCommand withManagerDocument(String managerDocument) {
            return CreateAccountCommand.builder()
                    .managerDocument(managerDocument)
                    .personDocument(personDocument)
                    .build();
        }
    }
}

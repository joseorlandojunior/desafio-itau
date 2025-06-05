package com.jose.junior.desafio_itau.person.useCase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

public interface EnablePersonUseCase {

    void execute(EnablePersonCommand cmd);

    @Builder
    @Getter
    @JsonIgnoreProperties(value = {"managerDocument"})
    class EnablePersonCommand {
        private final String managerDocument;
        private final String personDocument;

        public EnablePersonCommand withManagerDocument(String managerDocument) {
            return EnablePersonCommand.builder()
                    .managerDocument(managerDocument)
                    .personDocument(personDocument)
                    .build();
        }
    }
}
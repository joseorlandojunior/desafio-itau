package com.jose.junior.desafio_itau.person.useCase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

public interface DisablePersonUseCase {

    void execute(DisablePersonCommand cmd);

    @Builder
    @Getter
    @JsonIgnoreProperties(value = {"managerDocument"})
    class DisablePersonCommand {
        private final String managerDocument;

        @NotEmpty(message = "personDocument cannot be null")
        private final String personDocument;

        public DisablePersonCommand withManagerDocument(String managerDocument) {
            return DisablePersonCommand.builder()
                    .managerDocument(managerDocument)
                    .personDocument(personDocument)
                    .build();
        }
    }
}

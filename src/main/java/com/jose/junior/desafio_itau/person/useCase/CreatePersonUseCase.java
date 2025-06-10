package com.jose.junior.desafio_itau.person.useCase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public interface CreatePersonUseCase {
    void execute(CreatePersonCommand cmd);

    @Builder
    @Getter
    @JsonIgnoreProperties(value = {"managerDocument"})
    class CreatePersonCommand {
        private final String managerDocument;

        @NotEmpty(message = "Fullname cannot be null.")
        private final String fullName;

        @NotEmpty(message = "Document name cannot be null.")
        private final String document;

        @NotEmpty(message = "Email cannot be null.")
        private final String email;

        @NotEmpty(message = "Telephone name cannot be null.")
        private final String telephone;

        private final Boolean manageAccounts;
        private final LocalDate birthDate;

        public CreatePersonCommand withManageDocument(String managerDocument) {
            return CreatePersonCommand.builder()
                    .fullName(fullName)
                    .document(document)
                    .email(email)
                    .telephone(telephone)
                    .manageAccounts(manageAccounts)
                    .birthDate(birthDate)
                    .managerDocument(managerDocument)
                    .build();
        }
    }
}

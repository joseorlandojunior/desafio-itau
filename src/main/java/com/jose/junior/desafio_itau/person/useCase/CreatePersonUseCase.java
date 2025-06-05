package com.jose.junior.desafio_itau.person.useCase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
        private final String fullName;
        private final String document;
        private final String email;
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

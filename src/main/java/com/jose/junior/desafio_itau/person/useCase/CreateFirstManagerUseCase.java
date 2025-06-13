package com.jose.junior.desafio_itau.person.useCase;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public interface CreateFirstManagerUseCase {
    void execute(CreateFirstManagerCommand cmd);

    @Builder
    @Getter
    class CreateFirstManagerCommand {
        @NotEmpty(message = "Fullname cannot be null.")
        private final String fullName;

        @NotEmpty(message = "Document name cannot be null.")
        private final String document;

        @NotEmpty(message = "Email cannot be null.")
        private final String email;

        @NotEmpty(message = "Telephone name cannot be null.")
        private final String telephone;
        private final LocalDate birthDate;
    }
}

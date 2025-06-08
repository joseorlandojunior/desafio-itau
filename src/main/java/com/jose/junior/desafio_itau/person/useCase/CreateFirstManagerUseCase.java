package com.jose.junior.desafio_itau.person.useCase;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public interface CreateFirstManagerUseCase {
    void execute(CreateFirstManagerCommand cmd);

    @Builder
    @Getter
    class CreateFirstManagerCommand {
        private final String fullName;
        private final String document;
        private final String email;
        private final String telephone;
        private final LocalDate birthDate;
    }
}

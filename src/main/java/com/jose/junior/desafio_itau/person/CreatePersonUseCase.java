package com.jose.junior.desafio_itau.person;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public interface CreatePersonUseCase {
    void execute(CreatePersonCommand cmd);

    @Builder
    @Getter
    class CreatePersonCommand{
        private final String managerDocument;
        private final String fullName;
        private final String document;
        private final String email;
        private final String telephone;
        private final LocalDate birthDate;
    }
}

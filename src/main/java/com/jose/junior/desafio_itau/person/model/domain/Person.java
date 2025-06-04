package com.jose.junior.desafio_itau.person.model.domain;

import com.jose.junior.desafio_itau.account.validation.TelephoneBR;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class Person {

    private final String fullname;
    private final LocalDate birthDate;

    @NotEmpty(message = "CPF cannot be null")
    @CPF
    private final String document;

    @Email
    private final String email;

    @TelephoneBR
    private final String telephone;

    private final Boolean active;

    private final Boolean manageAccounts;
}

package com.jose.junior.desafio_itau.account.domain;

import com.jose.junior.desafio_itau.account.validation.AgeValid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final BigDecimal balance;

    @NotEmpty(message = "please enter the customer's name")
    private final String name;

    @NotEmpty(message = "CPF cannot be null")
    @CPF
    private final String document;

    @AgeValid
    @NotEmpty(message = "CEP cannot be null")
    private final LocalDate dateOfBirth;
}

package com.jose.junior.desafio_itau.person.model.domain;

import com.jose.junior.desafio_itau.account.model.domain.Account;
import com.jose.junior.desafio_itau.validation.TelephoneBR;
import com.jose.junior.desafio_itau.person.model.database.PersonDatabase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @NotEmpty(message = "Name cannot be null")
    private final String fullname;

    @NotEmpty(message = "Birth date cannot be null")
    private final LocalDate birthDate;

    @NotEmpty(message = "CPF cannot be null")
    @CPF
    private final String document;

    @Email
    private final String email;

    @TelephoneBR
    private final String telephone;

    private Boolean active;

    private Boolean manageAccounts;

    private final Account account;

    public void disable(){
        this.active = false;
    }

    public void enable(){
        this.active = true;
    }

    public PersonDatabase toDatabase(){
        return PersonDatabase.builder()
                .id(id)
                .manageAccounts(manageAccounts)
                .document(document)
                .birthDate(birthDate)
                .account(account)
                .fullname(fullname)
                .email(email)
                .telephone(telephone)
                .active(active)
                .build();
    }
}

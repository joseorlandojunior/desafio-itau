package com.jose.junior.desafio_itau.person.model.domain;

import com.jose.junior.desafio_itau.account.model.domain.Account;
import com.jose.junior.desafio_itau.person.model.database.PersonDatabase;
import com.jose.junior.desafio_itau.validation.TelephoneBR;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class Person {

    private Long id;

    @NotEmpty(message = "Name cannot be null")
    private String fullname;

    @NotNull(message = "Birth date cannot be null")
    private LocalDate birthDate;

    @NotEmpty(message = "CPF cannot be null")
    @CPF
    private String document;

    @Email
    private String email;

    @TelephoneBR
    private String telephone;

    private Boolean active;

    private Boolean manageAccounts;

    private Account account;

    public void disable() {
        this.active = false;
    }

    public void enable() {
        this.active = true;
    }

    public void assignAccount(Account account) {
       this.account = account;
    }

    public PersonDatabase toDatabase(boolean includeAccount) {
        return PersonDatabase.builder()
                .id(id)
                .manageAccounts(manageAccounts)
                .document(document)
                .birthDate(birthDate)
                .account(includeAccount && account != null ? account.toDatabase(false) : null)
                .fullName(fullname)
                .email(email)
                .telephone(telephone)
                .active(active)
                .build();
    }
}

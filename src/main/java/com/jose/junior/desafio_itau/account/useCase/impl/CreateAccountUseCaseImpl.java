package com.jose.junior.desafio_itau.account.useCase.impl;

import com.jose.junior.desafio_itau.account.model.domain.Account;
import com.jose.junior.desafio_itau.account.useCase.CreateAccountUseCase;
import com.jose.junior.desafio_itau.person.model.domain.Person;
import com.jose.junior.desafio_itau.person.useCase.impl.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CreateAccountUseCaseImpl implements CreateAccountUseCase {

    private final PersonService personService;
    private final AccountService accountService;

    @Override
    public void execute(CreateAccountCommand cmd) {

        personService.verifyIfManagerIsAuthorized(cmd.getManagerDocument());

        var person = personService.getPerson(cmd.getPersonDocument());
        var account = buildAccount(person);
        accountService.saveAccount(account);
    }

    private Account buildAccount(Person person) {
        return Account.builder()
                .balance(BigDecimal.ZERO)
                .client(person)
                .build();
    }
}

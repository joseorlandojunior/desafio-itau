package com.jose.junior.desafio_itau.account.useCase.impl;

import com.jose.junior.desafio_itau.account.model.domain.Account;
import com.jose.junior.desafio_itau.account.useCase.CreateAccountUseCase;
import com.jose.junior.desafio_itau.person.model.domain.Person;
import com.jose.junior.desafio_itau.person.useCase.impl.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateAccountUseCaseImpl implements CreateAccountUseCase {

    private final PersonService personService;
    private final AccountService accountService;

    @Override
    public void execute(CreateAccountCommand cmd) {
        var transactionInfoLog = String.format("CreateAccountUseCaseImpl_" + cmd.getPersonDocument());

        log.info("{} Payload received is {}", transactionInfoLog, cmd);

        personService.verifyIfManagerIsAuthorized(cmd.getManagerDocument());

        var person = personService.getPerson(cmd.getPersonDocument());
        var account = buildAccount(person);

        log.info("{} Account created is {}",transactionInfoLog, account);
        accountService.saveAccount(account, true);
    }

    private Account buildAccount(Person person) {
        return Account.builder()
                .balance(BigDecimal.ZERO)
                .client(person)
                .active(true)
                .build();
    }
}

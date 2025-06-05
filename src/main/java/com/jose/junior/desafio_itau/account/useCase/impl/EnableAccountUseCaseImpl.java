package com.jose.junior.desafio_itau.account.useCase.impl;

import com.jose.junior.desafio_itau.account.useCase.EnableAccountUseCase;
import com.jose.junior.desafio_itau.person.useCase.impl.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnableAccountUseCaseImpl implements EnableAccountUseCase {

    private final PersonService personService;
    private final AccountService accountService;

    @Override
    public void execute(EnableAccountCommand cmd) {
        personService.verifyIfManagerIsAuthorized(cmd.getManagerDocument());

        var account = accountService.getAccount(cmd.getAccountId());
        account.disable();
        accountService.saveAccount(account);
    }
}

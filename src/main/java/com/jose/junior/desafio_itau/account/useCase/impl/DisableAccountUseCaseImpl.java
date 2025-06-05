package com.jose.junior.desafio_itau.account.useCase.impl;

import com.jose.junior.desafio_itau.account.useCase.DisableAccountUseCase;
import com.jose.junior.desafio_itau.person.useCase.impl.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DisableAccountUseCaseImpl implements DisableAccountUseCase {

    private final PersonService personService;
    private final AccountService accountService;

    @Override
    public void execute(DisableAccountCommand cmd) {
        personService.verifyIfManagerIsAuthorized(cmd.getManagerDocument());

        var account = accountService.getAccount(cmd.getAccountId());
        account.enable();
        accountService.saveAccount(account);
    }
}

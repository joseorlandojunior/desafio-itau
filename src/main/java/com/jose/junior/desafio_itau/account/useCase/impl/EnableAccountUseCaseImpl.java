package com.jose.junior.desafio_itau.account.useCase.impl;

import com.jose.junior.desafio_itau.account.useCase.EnableAccountUseCase;
import com.jose.junior.desafio_itau.person.useCase.impl.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnableAccountUseCaseImpl implements EnableAccountUseCase {

    private final PersonService personService;
    private final AccountService accountService;

    @Override
    public void execute(EnableAccountCommand cmd) {
        var transactionInfoLog = String.format("EnableAccountUseCaseImpl_" + cmd.getAccountId());

        log.info("{} Payload received is: {}", transactionInfoLog, cmd);

        var account = accountService.getAccount(cmd.getAccountId());

        log.info("{} Account contain this data: {}", transactionInfoLog, cmd);

        personService.verifyIfManagerIsAuthorized(cmd.getManagerDocument());

        account.enable();

        accountService.saveAccount(account, false);
        log.info("{} Account {} has been enabled", transactionInfoLog, cmd.getAccountId());
    }
}

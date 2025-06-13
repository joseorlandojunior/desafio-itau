package com.jose.junior.desafio_itau.account.useCase.impl;

import com.jose.junior.desafio_itau.account.exception.InvalidBalanceException;
import com.jose.junior.desafio_itau.account.model.domain.Account;
import com.jose.junior.desafio_itau.account.useCase.DisableAccountUseCase;
import com.jose.junior.desafio_itau.person.useCase.impl.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class DisableAccountUseCaseImpl implements DisableAccountUseCase {

    private final PersonService personService;
    private final AccountService accountService;

    @Override
    public void execute(DisableAccountCommand cmd) {
        var transactionInfoLog = String.format("DisableAccountUseCaseImpl_" + cmd.getAccountId());

        log.info("{}Payload received is {}", transactionInfoLog, cmd);
        personService.verifyIfManagerIsAuthorized(cmd.getManagerDocument());

        var account = accountService.getAccount(cmd.getAccountId());
        disableAccount(account);
        accountService.saveAccount(account, true);
        log.info("{} Account  has been disabled {}", transactionInfoLog, account);
    }

    private void disableAccount(Account account) {
        if (Objects.nonNull(account)
                && account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new InvalidBalanceException("to deactivate the account, it is necessary to remove the balance.");
        }
        account.disable();
    }
}
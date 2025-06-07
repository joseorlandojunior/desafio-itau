package com.jose.junior.desafio_itau.account.useCase.impl;

import com.jose.junior.desafio_itau.account.exception.ValueForOperationInvalidException;
import com.jose.junior.desafio_itau.account.model.domain.Account;
import com.jose.junior.desafio_itau.account.useCase.AddBalanceUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
@Slf4j
public class AddBalanceUseCaseImpl implements AddBalanceUseCase {

    private final AccountService accountService;

    @Override
    public void execute(AddBalanceCommand cmd) {
        var transactionInfoLog = String.format("AddBalanceUseCaseImpl_" + cmd.getAccountId());

        log.info("{} Payload received is {}", transactionInfoLog, cmd);

        var account = accountService.getAccount(cmd.getAccountId());
        log.info("{} Account contain this data: {}", transactionInfoLog, account);

        if (cmd.getValueForAdd().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValueForOperationInvalidException("The value to be added must be positive");
        }

        addValue(account, cmd);
        log.info("{} Balance has been successfully added.", transactionInfoLog);
    }

    private void addValue(Account account, AddBalanceCommand cmd) {
        if (cmd.getDocumentAccountOwner().equals(account.getClient().getDocument())
                && account.getActive() == true) {
            account.addBalance(cmd.getValueForAdd());
            accountService.saveAccount(account, true);
        }
    }
}

package com.jose.junior.desafio_itau.account.useCase.impl;

import com.jose.junior.desafio_itau.account.exception.ValueForOperationInvalidException;
import com.jose.junior.desafio_itau.account.model.domain.Account;
import com.jose.junior.desafio_itau.account.useCase.DebitBalanceUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class DebitBalanceUseCaseImpl implements DebitBalanceUseCase {

    private final AccountService accountService;

    @Override
    public void execute(DebitBalanceCommand cmd) {
        var transactionInfoLog = String.format("DebitBalanceUseCaseImpl_" + cmd.getAccountId());

        log.info("{} Payload received is {}", transactionInfoLog, cmd);
        var account = accountService.getAccount(cmd.getAccountId());
        log.info("{} Account contain this data: {}", transactionInfoLog, account);


        if (cmd.getValueForDebit().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValueForOperationInvalidException("The value to be debit is invalid.");
        }

        debitValue(account, cmd);
        log.info("{} Balance has been successfully debited.", transactionInfoLog);
    }

    private void debitValue(Account account, DebitBalanceCommand cmd) {
        if (cmd.getDocumentAccountOwner().equals(account.getClient().getDocument())) {
            balanceSuficient(account, cmd.getValueForDebit());
            account.debitBalance(cmd.getValueForDebit());
            accountService.saveAccount(account, true);
        }
    }

    private void balanceSuficient(Account account, BigDecimal balanceForDebit) {
        if (balanceForDebit.compareTo(account.getBalance()) > 0) {
            throw new ValueForOperationInvalidException("The value to be debit is invalid.");
        }
    }
}
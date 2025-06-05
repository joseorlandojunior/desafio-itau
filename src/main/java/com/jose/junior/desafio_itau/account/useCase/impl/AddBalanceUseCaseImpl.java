package com.jose.junior.desafio_itau.account.useCase.impl;

import com.jose.junior.desafio_itau.account.exception.ValueForAddInvalidException;
import com.jose.junior.desafio_itau.account.model.domain.Account;
import com.jose.junior.desafio_itau.account.useCase.AddBalanceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class AddBalanceUseCaseImpl implements AddBalanceUseCase {

    private final AccountService accountService;

    @Override
    public void execute(AddBalanceCommand cmd) {
        var account = accountService.getAccount(cmd.getAccountId());

        if (cmd.getValueForAdd().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValueForAddInvalidException("The value to be added must be positive");
        }

        addValue(account, cmd);
    }

    private void addValue(Account account, AddBalanceCommand cmd) {
        if (account.getClient().getDocument().equals(cmd.getDocumentAccountOwner())) {
            account.addBalance(cmd.getValueForAdd());
            accountService.saveAccount(account);
        }
    }
}

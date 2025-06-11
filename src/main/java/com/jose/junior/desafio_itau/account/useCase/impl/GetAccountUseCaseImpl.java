package com.jose.junior.desafio_itau.account.useCase.impl;

import com.jose.junior.desafio_itau.account.model.domain.Account;
import com.jose.junior.desafio_itau.account.useCase.GetAccountUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAccountUseCaseImpl implements GetAccountUseCase {

    private final AccountService service;

    @Override
    public AccountDTO execute(String accountOwner) {
        var transactionInfoLog = String.format("GetAccountUseCaseImpl_" + accountOwner);

        var account = service.getAccountByDocumentOwner(accountOwner);
        log.info("{} AccountData is: {}", transactionInfoLog, account);
        return buildAccountDto(account);
    }

    private AccountDTO buildAccountDto(Account account) {
        return AccountDTO.builder()
                .id(account.getId())
                .documentOwner(account.getClient().getDocument())
                .active(account.getActive())
                .balance(account.getBalance())
                .build();
    }
}

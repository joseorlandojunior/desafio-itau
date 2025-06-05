package com.jose.junior.desafio_itau.account.useCase.impl;

import com.jose.junior.desafio_itau.account.exception.AccountNotFoundException;
import com.jose.junior.desafio_itau.account.gateway.database.AccountRepository;
import com.jose.junior.desafio_itau.account.model.database.AccountDatabase;
import com.jose.junior.desafio_itau.account.model.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    public void saveAccount(Account account) {
        repository.save(account.toDatabase());
    }

    public Account getAccount(Long accountId) {
        return repository.findById(accountId)
                .map(AccountDatabase::toDomain)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account with id %d not found", accountId)));
    }
}
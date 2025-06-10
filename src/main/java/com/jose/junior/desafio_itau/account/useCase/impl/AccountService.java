package com.jose.junior.desafio_itau.account.useCase.impl;

import com.jose.junior.desafio_itau.account.exception.AccountNotFoundException;
import com.jose.junior.desafio_itau.account.gateway.database.AccountRepository;
import com.jose.junior.desafio_itau.account.model.database.AccountDatabase;
import com.jose.junior.desafio_itau.account.model.domain.Account;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    public AccountDatabase saveAccount(Account account, Boolean includePerson) {
        return repository.save(account.toDatabase(includePerson));
    }

    public Account getAccount(Long accountId) {
        return repository.findById(accountId)
                .map(account -> account.toDomain(true))
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account with id %d not found", accountId)));
    }
}
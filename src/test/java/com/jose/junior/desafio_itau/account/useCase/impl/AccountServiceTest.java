package com.jose.junior.desafio_itau.account.useCase.impl;


import com.jose.junior.desafio_itau.account.gateway.database.AccountRepository;
import com.jose.junior.desafio_itau.account.model.database.AccountDatabase;
import com.jose.junior.desafio_itau.person.model.database.PersonDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AccountServiceTest {

    @Autowired
    private AccountService service;

    @MockitoBean
    private AccountRepository repository;

    @Test
    public void souldReturnAccountDatabaseWhenSaveAccount() {

        var personDatabase = PersonDatabase.builder()
                .birthDate(LocalDate.of(1985, 9, 1))
                .document("26204434071")
                .email("joao@teste.com")
                .fullName("João da silva")
                .manageAccounts(false)
                .active(false)
                .telephone("123345567")
                .build();

        var account = AccountDatabase.builder()
                .balance(BigDecimal.ZERO)
                .active(true)
                .client(personDatabase)
                .build();

        Mockito.when(repository.save(any())).thenReturn(account);

        var result = service.saveAccount(account.toDomain(true), true);

        Assertions.assertAll("Validate data returned in service.",
                () -> assertEquals(result.getBalance(), account.getBalance()),
                () -> assertEquals(Boolean.TRUE, result.getActive()),
                () -> assertEquals(result.getClient().getDocument(), account.getClient().getDocument())
        );
    }

    @Test
    public void shouldReturnAccountInGetAccount() {
        var personDatabase = PersonDatabase.builder()
                .birthDate(LocalDate.of(1985, 9, 1))
                .document("26204434071")
                .email("joao@teste.com")
                .fullName("João da silva")
                .manageAccounts(false)
                .active(false)
                .telephone("123345567")
                .build();

        var account = AccountDatabase.builder()
                .balance(BigDecimal.ZERO)
                .active(true)
                .client(personDatabase)
                .id(1L)
                .build();

        Mockito.when(repository.findById(any())).thenReturn(Optional.of(account));

        var result = service.getAccount(1L);

        Assertions.assertAll("Validate data returned in service.",
                () -> assertEquals(result.getBalance(), account.getBalance()),
                () -> assertEquals(Boolean.TRUE, result.getActive()),
                () -> assertEquals(result.getClient().getDocument(), account.getClient().getDocument())
        );
    }

    @Test
    public void shouldReturnAccountInGetAccountByDocumentOwner() {
        var personDatabase = PersonDatabase.builder()
                .birthDate(LocalDate.of(1985, 9, 1))
                .document("26204434071")
                .email("joao@teste.com")
                .fullName("João da silva")
                .manageAccounts(false)
                .active(false)
                .telephone("123345567")
                .build();

        var account = AccountDatabase.builder()
                .balance(BigDecimal.ZERO)
                .active(true)
                .client(personDatabase)
                .id(1L)
                .build();

        Mockito.when(repository.findByClientDocument(any())).thenReturn(Optional.of(account));

        var result = service.getAccountByDocumentOwner(personDatabase.getDocument());

        Assertions.assertAll("Validate data returned in service.",
                () -> assertEquals(result.getBalance(), account.getBalance()),
                () -> assertEquals(Boolean.TRUE, result.getActive()),
                () -> assertEquals(result.getClient().getDocument(), account.getClient().getDocument())
        );
    }
}

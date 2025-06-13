package com.jose.junior.desafio_itau.account.useCase.impl;

import com.jose.junior.desafio_itau.account.exception.BloquedException;
import com.jose.junior.desafio_itau.account.exception.ValueForOperationInvalidException;
import com.jose.junior.desafio_itau.account.model.domain.Account;
import com.jose.junior.desafio_itau.account.useCase.DebitBalanceUseCase;
import com.jose.junior.desafio_itau.account.useCase.DebitBalanceUseCase.DebitBalanceCommand;
import com.jose.junior.desafio_itau.person.model.domain.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DebitBalanceUseCaseImplTest {

    @Autowired
    DebitBalanceUseCase useCase;

    @MockitoBean
    AccountService accountService;

    @Test
    public void shouldThrowValueForOperationInvalidExceptionWhenValueForDebitIsInvalid() {

        var cmd = DebitBalanceCommand.builder()
                .valueForDebit(BigDecimal.valueOf(-1222.50))
                .accountId(1L)
                .documentAccountOwner("37483673095")
                .build();

        Assertions.assertThrows(ValueForOperationInvalidException.class, () -> useCase.execute(cmd));
    }

    @Test
    public void shouldThrowValueForOperationInvalidExceptionWhenValueForDebitIsNotSuficient() {

        var client = Person.builder()
                .manageAccounts(false)
                .email("teste@teste.com")
                .telephone("1197543210")
                .active(true)
                .document("08040536006")
                .birthDate(LocalDate.of(1999, 2, 1))
                .fullname("Cliente para teste")
                .id(1L)
                .build();

        var account = Account.builder()
                .active(true)
                .balance(BigDecimal.valueOf(1234.55))
                .client(client)
                .id(1L)
                .build();

        when(accountService.getAccount(any())).thenReturn(account);

        var cmd = DebitBalanceCommand.builder()
                .valueForDebit(BigDecimal.valueOf(5000.50))
                .accountId(1L)
                .documentAccountOwner(client.getDocument())
                .build();

        Assertions.assertThrows(ValueForOperationInvalidException.class, () -> useCase.execute(cmd));
    }

    @Test
    public void shouldThrowBloquedExceptionWhenPesonIsNotOwnerOfAccount() {

        var client = Person.builder()
                .manageAccounts(false)
                .email("teste@teste.com")
                .telephone("1197543210")
                .active(true)
                .document("08040536006")
                .birthDate(LocalDate.of(1999, 2, 1))
                .fullname("Cliente para teste")
                .id(1L)
                .build();

        var account = Account.builder()
                .active(true)
                .balance(BigDecimal.valueOf(1234.55))
                .client(client)
                .id(1L)
                .build();

        when(accountService.getAccount(any())).thenReturn(account);

        var cmd = DebitBalanceCommand.builder()
                .valueForDebit(BigDecimal.valueOf(5000.50))
                .accountId(1L)
                .documentAccountOwner("80381874079")
                .build();

        Assertions.assertThrows(BloquedException.class, () -> useCase.execute(cmd));
    }
}
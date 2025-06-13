package com.jose.junior.desafio_itau.account.useCase.impl;

import com.jose.junior.desafio_itau.account.exception.BloquedException;
import com.jose.junior.desafio_itau.account.exception.ValueForOperationInvalidException;
import com.jose.junior.desafio_itau.account.model.domain.Account;
import com.jose.junior.desafio_itau.account.useCase.AddBalanceUseCase;
import com.jose.junior.desafio_itau.account.useCase.AddBalanceUseCase.AddBalanceCommand;
import com.jose.junior.desafio_itau.person.model.domain.Person;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AddBalanceUseCaseImplTest {

    @Autowired
    private AddBalanceUseCase useCase;

    @MockitoBean
    private AccountService service;

    @Test
    public void shouldReturnValueForOperationInvalidExceptionWhenValueForAddIsInvalid() {
        var cmd = AddBalanceCommand.builder()
                .valueForAdd(BigDecimal.valueOf(-1234.90))
                .accountId(1L)
                .build();

        assertThrows(ValueForOperationInvalidException.class, () -> useCase.execute(cmd));
    }

    @Test
    public void shouldReturnBloquedExceptionWhenAccountIsNotActive() {
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
                .active(false)
                .balance(BigDecimal.valueOf(1234.55))
                .id(1L)
                .client(client)
                .build();

        Mockito.when(service.getAccount(Mockito.any())).thenReturn(account);

        var cmd = AddBalanceCommand.builder()
                .valueForAdd(BigDecimal.valueOf(1234.90))
                .accountId(account.getId())
                .documentAccountOwner(client.getDocument())
                .build();

        assertThrows(BloquedException.class, () -> useCase.execute(cmd));
    }

    @Test
    public void shouldReturnBloquedExceptionWhenPersonIsNotOwnerOfThisAccount() {

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
                .id(1L)
                .client(client)
                .build();

        Mockito.when(service.getAccount(Mockito.any())).thenReturn(account);

        var cmd = AddBalanceCommand.builder()
                .valueForAdd(BigDecimal.valueOf(1234.90))
                .accountId(account.getId())
                .documentAccountOwner("37483673095")
                .build();

        assertThrows(BloquedException.class, () -> useCase.execute(cmd));
    }
}
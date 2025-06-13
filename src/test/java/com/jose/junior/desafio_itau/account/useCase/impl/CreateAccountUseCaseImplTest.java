package com.jose.junior.desafio_itau.account.useCase.impl;

import com.jose.junior.desafio_itau.account.model.database.AccountDatabase;
import com.jose.junior.desafio_itau.account.useCase.CreateAccountUseCase;
import com.jose.junior.desafio_itau.account.useCase.CreateAccountUseCase.CreateAccountCommand;
import com.jose.junior.desafio_itau.person.model.domain.Person;
import com.jose.junior.desafio_itau.person.useCase.impl.PersonService;
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
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CreateAccountUseCaseImplTest {

    @MockitoBean
    private AccountService service;

    @MockitoBean
    private PersonService personService;

    @Autowired
    private CreateAccountUseCase useCase;

    @Test
    void shouldCreateAccountWithSuccess() {

        var cmd = CreateAccountCommand.builder()
                .personDocument("08040536006")
                .managerDocument("70431749027")
                .build();

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

        var accountDatabase = AccountDatabase.builder()
                .active(true)
                .balance(BigDecimal.ZERO)
                .client(client.toDatabase(true))
                .id(1L)
                .build();

        when(personService.getPerson(cmd.getPersonDocument())).thenReturn(client);

        when(service.saveAccount(any(), any())).thenReturn(accountDatabase);

        var result = useCase.execute(cmd);

        Assertions.assertEquals(1L, result);

        verify(personService).verifyIfManagerIsAuthorized(cmd.getManagerDocument());

        verify(personService).getPerson(cmd.getPersonDocument());

        verify(personService).save(client, true);

        verify(service).saveAccount(any(), eq(true));
    }
}
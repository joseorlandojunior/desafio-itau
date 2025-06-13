package com.jose.junior.desafio_itau.account.useCase.impl;

import com.jose.junior.desafio_itau.account.model.domain.Account;
import com.jose.junior.desafio_itau.account.useCase.GetAccountUseCase;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GetAccountUseCaseImplTest {

    @MockitoBean
    private AccountService service;

    @Autowired
    private GetAccountUseCase useCase;

    @Test
    public void shouildReturnAccountDataWithSuccess() {

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

        Mockito.when(service.getAccountByDocumentOwner(anyString())).thenReturn(account);

        var result = useCase.execute(client.getDocument());

        assertAll("Validate account data return.",
                () -> assertEquals(result.getBalance(), account.getBalance()),
                () -> assertEquals(result.getDocumentOwner(), client.getDocument()),
                () -> assertEquals(result.getId(), account.getId()),
                () -> assertTrue(result.getActive())
        );
    }
}
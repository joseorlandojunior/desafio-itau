package com.jose.junior.desafio_itau.person.usecase.impl;

import com.jose.junior.desafio_itau.person.exception.PersonAlreadyExistsException;
import com.jose.junior.desafio_itau.person.gateway.database.PersonRepository;
import com.jose.junior.desafio_itau.person.useCase.CreatePersonUseCase;
import com.jose.junior.desafio_itau.person.useCase.impl.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CreatePersonUseCaseImplTest {

    @Autowired
    private CreatePersonUseCase useCase;

    @Autowired
    private PersonRepository personRepository;

    @MockitoBean
    private PersonService service;

    @Test
    public void shouldThrowPersonAlreadyExistsExceptionWhenPersonAlreadyExists() {

        var cmd = CreatePersonUseCase.CreatePersonCommand.builder()
                .birthDate(LocalDate.of(1997, 8, 8))
                .document("44162468702")
                .email("teste@teste2.com")
                .fullName("João da Silva")
                .manageAccounts(false)
                .telephone("123345567")
                .build();

        Mockito.when(service.existsByDocument(cmd.getDocument())).thenReturn(true);

        Assertions.assertThrows(PersonAlreadyExistsException.class, () -> useCase.execute(cmd));
    }
}

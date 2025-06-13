package com.jose.junior.desafio_itau.person.usecase.impl;

import com.jose.junior.desafio_itau.person.exception.ManagerNotAuthorizedException;
import com.jose.junior.desafio_itau.person.exception.PersonNotFoundException;
import com.jose.junior.desafio_itau.person.gateway.database.PersonRepository;
import com.jose.junior.desafio_itau.person.model.domain.Person;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PersonServiceTest {

    @Autowired
    private PersonService service;

    @MockitoBean
    private PersonRepository repository;

    @Test
    public void shouldThrowsExceptionWhenManagerIsNotAuthorized() {

        Assertions.assertThrows(ManagerNotAuthorizedException.class, () -> service.verifyIfManagerIsAuthorized("62159175080"));
    }

    @Test
    public void shouldThrowsExceptionWhenNotFoundPerson() {

        Assertions.assertThrows(PersonNotFoundException.class, () -> service.getPerson("62159175080"));
    }

    @Test
    public void shouldReturnPersonWithSuccess() {

        var person = Person.builder()
                .manageAccounts(false)
                .email("teste@teste.com")
                .telephone("1197543210")
                .active(true)
                .document("08040536006")
                .birthDate(LocalDate.of(1999, 2, 1))
                .fullname("Cliente para teste")
                .id(1L)
                .build();

        Mockito.when(repository.getByDocument(person.getDocument())).thenReturn(Optional.of(person.toDatabase(true)));

        var result = service.getPerson(person.getDocument());

        Assertions.assertAll("ValidateDateReturnedForGetPerson",
                () -> assertEquals(result.getEmail(), person.getEmail()),
                () -> assertEquals(result.getTelephone(), person.getTelephone()),
                () -> assertEquals(result.getDocument(), person.getDocument()),
                () -> assertEquals(result.getBirthDate(), person.getBirthDate()),
                () -> assertEquals(result.getFullname(), person.getFullname()),
                () -> assertFalse(result.getManageAccounts()),
                () -> assertTrue(result.getActive())
        );
    }

    @Test
    public void shouldReturnTrueWhenPersonExistsInDb() {
        var person = Person.builder()
                .manageAccounts(false)
                .email("teste@teste.com")
                .telephone("1197543210")
                .active(true)
                .document("08040536006")
                .birthDate(LocalDate.of(1999, 2, 1))
                .fullname("Cliente para teste")
                .id(1L)
                .build();

        Mockito.when(repository.existsByDocument(person.getDocument())).thenReturn(true);

        var result = service.existsByDocument(person.getDocument());

        assertTrue(result);
    }

    @Test
    public void shouldReturnTrueWhenManagerRegisteredInDbAndIsActive() {

        Mockito.when(repository.existsByDocumentAndActiveAndManageAccounts("62159175080", true, true)).thenReturn(true);

        var result = service.existsByDocumentAndActive("62159175080", true);

        assertTrue(result);
    }
}
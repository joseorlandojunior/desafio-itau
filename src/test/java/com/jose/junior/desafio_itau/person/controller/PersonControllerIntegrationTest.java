package com.jose.junior.desafio_itau.person.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jose.junior.desafio_itau.person.exception.ManagerNotAuthorizedException;
import com.jose.junior.desafio_itau.person.gateway.database.PersonRepository;
import com.jose.junior.desafio_itau.person.model.database.PersonDatabase;
import com.jose.junior.desafio_itau.person.useCase.CreatePersonUseCase;
import com.jose.junior.desafio_itau.person.useCase.CreatePersonUseCase.CreatePersonCommand;
import com.jose.junior.desafio_itau.person.useCase.DisablePersonUseCase.DisablePersonCommand;
import com.jose.junior.desafio_itau.person.useCase.EnablePersonUseCase.EnablePersonCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(PER_CLASS)
@ActiveProfiles("test")
class PersonControllerIntegrationTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CreatePersonUseCase createPersonUseCase;

    @Autowired
    PersonRepository personRepository;

    private static final String PERSON_ENDPOINT = PersonController.PATH;

    @BeforeEach
    public void beforeAll() {
        personRepository.deleteAll();
    }

    @Test
    @DisplayName("Ensures that a person has been registered successfully and with correct data")
    public void shouldCreatePersonWithSuccess() throws Exception {

        var manager = PersonDatabase.builder()
                .birthDate(LocalDate.of(1990, 2, 3))
                .document("66721724243")
                .email("teste@teste.com")
                .fullName("Alisson Santos")
                .manageAccounts(true)
                .active(true)
                .telephone("123345567")
                .build();
        personRepository.save(manager);

        var cmd = CreatePersonCommand.builder()
                .birthDate(LocalDate.of(1997, 8, 8))
                .document("44162468702")
                .email("teste@teste2.com")
                .fullName("João da Silva")
                .manageAccounts(false)
                .telephone("123345567")
                .build();

        mvc.perform(post(PERSON_ENDPOINT, manager.getDocument())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cmd)))
                .andExpect(status().isCreated());

        var result = personRepository.getByDocument(cmd.getDocument()).get().toDomain(false);

        assertAll("ensuring that the person was successfully registered",
                () -> assertEquals(result.getDocument(), cmd.getDocument()),
                () -> assertEquals(result.getEmail(), cmd.getEmail()),
                () -> assertEquals(result.getFullname(), cmd.getFullName()),
                () -> assertEquals(result.getBirthDate(), cmd.getBirthDate()),
                () -> assertEquals(result.getTelephone(), cmd.getTelephone()),
                () -> assertTrue(result.getActive()),
                () -> assertFalse(result.getManageAccounts())
        );
    }

    @Test
    @DisplayName("Ensures that a person has had their registration successfully enabled")
    public void shoudlEnablePersonWithSuccess() throws Exception {

        var manager = PersonDatabase.builder()
                .birthDate(LocalDate.of(1990, 2, 3))
                .document("66721724243")
                .email("teste@teste.com")
                .fullName("Alisson Santos")
                .manageAccounts(true)
                .active(true)
                .telephone("123345567")
                .build();
        personRepository.save(manager);

        var personDatabase = PersonDatabase.builder()
                .birthDate(LocalDate.of(1985, 9, 1))
                .document("26204434071")
                .email("joao@teste.com")
                .fullName("João da silva")
                .manageAccounts(false)
                .active(false)
                .telephone("123345567")
                .build();
        personRepository.save(personDatabase);


        var cmd = EnablePersonCommand.builder()
                .personDocument(personDatabase.getDocument())
                .build();

        mvc.perform(put(PERSON_ENDPOINT.concat("/enable"), manager.getDocument())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cmd)))
                .andExpect(status().isOk());

        var result = personRepository.getByDocument(cmd.getPersonDocument()).get().toDomain(false);

        assertTrue(result.getActive());

        assertAll("Ensures that no further information has been modified",
                () -> assertEquals(result.getBirthDate(), personDatabase.getBirthDate()),
                () -> assertEquals(result.getTelephone(), personDatabase.getTelephone()),
                () -> assertFalse(result.getManageAccounts()),
                () -> assertEquals(result.getDocument(), personDatabase.getDocument()),
                () -> assertEquals(result.getFullname(), personDatabase.getFullName()),
                () -> assertEquals(result.getEmail(), personDatabase.getEmail())
        );
    }

    @Test
    @DisplayName("Ensures that a person has had their registration successfully disabled")
    public void shoudlDisablePersonWithSuccess() throws Exception {

        var manager = PersonDatabase.builder()
                .birthDate(LocalDate.of(1990, 2, 3))
                .document("66721724243")
                .email("teste@teste.com")
                .fullName("Alisson Santos")
                .manageAccounts(true)
                .active(true)
                .telephone("123345567")
                .build();
        personRepository.save(manager);

        var personDatabase = PersonDatabase.builder()
                .birthDate(LocalDate.of(1985, 9, 1))
                .document("26204434071")
                .email("joao@teste.com")
                .fullName("João da silva")
                .manageAccounts(false)
                .active(true)
                .telephone("123345567")
                .build();
        personRepository.save(personDatabase);


        var cmd = DisablePersonCommand.builder()
                .personDocument(personDatabase.getDocument())
                .build();

        mvc.perform(put(PERSON_ENDPOINT.concat("/disable"), manager.getDocument())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cmd)))
                .andExpect(status().isOk());

        var result = personRepository.getByDocument(cmd.getPersonDocument()).get().toDomain(false);

        assertFalse(result.getActive());

        assertAll("Ensures that no further information has been modified",
                () -> assertEquals(result.getBirthDate(), personDatabase.getBirthDate()),
                () -> assertEquals(result.getTelephone(), personDatabase.getTelephone()),
                () -> assertFalse(result.getManageAccounts()),
                () -> assertEquals(result.getDocument(), personDatabase.getDocument()),
                () -> assertEquals(result.getFullname(), personDatabase.getFullName()),
                () -> assertEquals(result.getEmail(), personDatabase.getEmail())
        );
    }

    @Test
    @DisplayName("Ensure that when trying to register a person using an invalid manager document, the return will be unprocessable entity")
    public void shouldReturnHttpStatus422WhenManagerIsInvalidForCreatePerson() throws Exception {

        var manager = PersonDatabase.builder()
                .birthDate(LocalDate.of(1990, 2, 3))
                .document("66721724243")
                .email("teste@teste.com")
                .fullName("Alisson Santos")
                .manageAccounts(false)
                .active(true)
                .telephone("123345567")
                .build();
        personRepository.save(manager);

        var cmd = CreatePersonCommand.builder()
                .birthDate(LocalDate.of(1997, 8, 8))
                .document("44162468702")
                .email("teste@teste2.com")
                .fullName("João da Silva")
                .manageAccounts(false)
                .telephone("123345567")
                .build();

        mvc.perform(post(PERSON_ENDPOINT, manager.getDocument())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cmd)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(result -> assertThatThrownBy(() -> createPersonUseCase.execute(cmd)).isInstanceOf(ManagerNotAuthorizedException.class));
    }
}
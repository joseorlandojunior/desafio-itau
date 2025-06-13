package com.jose.junior.desafio_itau.person.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jose.junior.desafio_itau.person.exception.PersonAlreadyExistsException;
import com.jose.junior.desafio_itau.person.gateway.database.PersonRepository;
import com.jose.junior.desafio_itau.person.model.database.PersonDatabase;
import com.jose.junior.desafio_itau.person.useCase.CreateFirstManagerUseCase;
import com.jose.junior.desafio_itau.person.useCase.CreateFirstManagerUseCase.CreateFirstManagerCommand;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(PER_CLASS)
@ActiveProfiles("test")
class CreateFirstManagerControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CreateFirstManagerUseCase createPersonUseCase;

    @Autowired
    PersonRepository personRepository;

    private static final String FIRST_MANAGER_ENDPOINT = CreateFirstManagerController.PATH;

    @BeforeEach
    public void beforeAll() {
        personRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create first manager.")
    public void shouldCreateFirstManagerWithSuccess() throws Exception {

        var cmd = CreateFirstManagerCommand.builder()
                .birthDate(LocalDate.of(1997, 8, 8))
                .document("44162468702")
                .email("teste@teste2.com")
                .fullName("João da Silva")
                .telephone("123345567")
                .build();

        mvc.perform(post(FIRST_MANAGER_ENDPOINT)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cmd)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("If already manager is registeres, sould return PersonAlreadyExistsException")
    public void shouldReturnExceptionWhenAnyManagerAlreadyRegistered() throws Exception {

        var personDatabase = PersonDatabase.builder()
                .birthDate(LocalDate.of(1985, 9, 1))
                .document("26204434071")
                .email("joao@teste.com")
                .fullName("João da silva")
                .manageAccounts(true)
                .active(true)
                .telephone("123345567")
                .build();
        personRepository.save(personDatabase);

        var cmd = CreateFirstManagerCommand.builder()
                .birthDate(LocalDate.of(1997, 8, 8))
                .document("44162468702")
                .email("teste@teste2.com")
                .fullName("João da Silva")
                .telephone("123345567")
                .build();

        var result = mvc.perform(post(FIRST_MANAGER_ENDPOINT)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cmd)))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(result.getResolvedException()).isInstanceOf(PersonAlreadyExistsException.class);
    }

    @Test
    @DisplayName("Should throw PersonAlreadyExistsException when already exists any person with this document")
    public void shouldReturnPersonAlreadyExistsException() throws Exception {
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

        var cmd = CreateFirstManagerCommand.builder()
                .birthDate(LocalDate.of(1985, 9, 1))
                .document("26204434071")
                .email("joao@teste.com")
                .fullName("João da silva")
                .telephone("123345567")
                .build();

        var result = mvc.perform(post(FIRST_MANAGER_ENDPOINT)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cmd)))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(result.getResolvedException()).isInstanceOf(PersonAlreadyExistsException.class);
    }
}
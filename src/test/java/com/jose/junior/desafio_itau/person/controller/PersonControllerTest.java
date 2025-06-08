package com.jose.junior.desafio_itau.person.controller;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.jose.junior.desafio_itau.person.gateway.database.PersonRepository;
import com.jose.junior.desafio_itau.person.model.database.PersonDatabase;
import com.jose.junior.desafio_itau.person.useCase.CreatePersonUseCase.CreatePersonCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static com.jose.junior.desafio_itau.TestUtils.objectToJson;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith({SpringExtension.class})
@ActiveProfiles("test")
class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    PersonRepository personRepository;

    private static final String PERSON_ENDPOINT = "/desafio-itau/person";

    @Test
    public void shouldCreatePersonWithSuccess() throws Exception {

        var manager = PersonDatabase.builder()
                .birthDate(LocalDate.of(1990, 2, 3))
                .document("66721724243")
                .email("teste@teste.com")
                .fullName("José Orlando Junior")
                .manageAccounts(true)
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
                        .content(objectToJson(cmd)))
                .andExpect(status().isCreated());

        var result = personRepository.getByDocument(cmd.getDocument());

        //asserts
    }
}
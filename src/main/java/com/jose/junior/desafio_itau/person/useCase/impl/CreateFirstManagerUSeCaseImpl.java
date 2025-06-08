package com.jose.junior.desafio_itau.person.useCase.impl;

import com.jose.junior.desafio_itau.person.model.domain.Person;
import com.jose.junior.desafio_itau.person.useCase.CreateFirstManagerUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CreateFirstManagerUSeCaseImpl implements CreateFirstManagerUseCase {

    private final PersonService service;

    @Override
    public void execute(CreateFirstManagerCommand cmd) {
        var transactionInfoLog = String.format("CreateFirstManagerUSeCaseImpl_" + cmd.getDocument());

        log.info("{} Payload received is {}", transactionInfoLog, cmd);

        var person = buildPerson(cmd);
        service.save(person, false);
        log.info("{} First manager is: {}", transactionInfoLog, person);
    }

    private Person buildPerson(CreateFirstManagerCommand cmd) {
        return Person.builder()
                .active(true)
                .email(cmd.getEmail())
                .birthDate(cmd.getBirthDate())
                .document(cmd.getDocument())
                .manageAccounts(true)
                .fullname(cmd.getFullName())
                .telephone(cmd.getTelephone())
                .build();
    }
}

package com.jose.junior.desafio_itau.person.useCase.impl;

import com.jose.junior.desafio_itau.person.exception.PersonAlreadyExistsException;
import com.jose.junior.desafio_itau.person.model.domain.Person;
import com.jose.junior.desafio_itau.person.useCase.CreatePersonUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreatePersonUseCaseImpl implements CreatePersonUseCase {

    private final PersonService service;

    @Override
    public void execute(CreatePersonCommand cmd) {
        var transactionInfoLog = String.format("CreatePersonUseCaseImpl_" + cmd.getDocument());

        log.info("{} Payload received is {}", transactionInfoLog, cmd);

        verifyIfShouldCreatePerson(cmd);

        var person = buildPerson(cmd);
        service.save(person, true);
        log.info("{} Person saved in database: {}", transactionInfoLog, person);
    }

    private void verifyIfShouldCreatePerson(CreatePersonCommand cmd) {
        service.verifyIfManagerIsAuthorized(cmd.getManagerDocument());
        if (service.existsByDocument(cmd.getDocument())) {
            throw new PersonAlreadyExistsException(String.format("Person with document %s already exists.", cmd.getDocument()));
        }
    }

    private Person buildPerson(CreatePersonCommand cmd) {
        return Person.builder()
                .active(true)
                .email(cmd.getEmail())
                .birthDate(cmd.getBirthDate())
                .document(cmd.getDocument())
                .manageAccounts(cmd.getManageAccounts())
                .fullname(cmd.getFullName())
                .telephone(cmd.getTelephone())
                .build();
    }
}

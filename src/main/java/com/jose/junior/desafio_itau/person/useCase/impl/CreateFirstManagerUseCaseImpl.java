package com.jose.junior.desafio_itau.person.useCase.impl;

import com.jose.junior.desafio_itau.person.exception.PersonAlreadyExistsException;
import com.jose.junior.desafio_itau.person.model.domain.Person;
import com.jose.junior.desafio_itau.person.useCase.CreateFirstManagerUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CreateFirstManagerUseCaseImpl implements CreateFirstManagerUseCase {

    private final PersonService service;

    @Override
    public void execute(CreateFirstManagerCommand cmd) {
        var transactionInfoLog = String.format("CreateFirstManagerUSeCaseImpl_" + cmd.getDocument());

        log.info("{} Payload received is {}", transactionInfoLog, cmd);

        verifyIfFirstManager();
        verifyIfPersonExists(cmd.getDocument());

        var person = buildPerson(cmd);
        service.save(person, false);
        log.info("{} First manager is: {}", transactionInfoLog, person);
    }

    private void verifyIfPersonExists(String document) {
        if (service.existsByDocument(document)) {
            throw new PersonAlreadyExistsException(String.format("Person with document %s already exists.", document));
        }
    }

    private void verifyIfFirstManager() {
        if (service.existsManager()) {
            throw new PersonAlreadyExistsException("There is already a registered manager");
        }
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

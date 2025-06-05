package com.jose.junior.desafio_itau.person.useCase.impl;

import com.jose.junior.desafio_itau.person.exception.ManagerNotAuthorizedException;
import com.jose.junior.desafio_itau.person.exception.PersonNotFoundException;
import com.jose.junior.desafio_itau.person.gateway.database.PersonRepository;
import com.jose.junior.desafio_itau.person.model.database.PersonDatabase;
import com.jose.junior.desafio_itau.person.model.domain.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repository;

    public Boolean existsByDocumentAndActive(String document, Boolean active) {
        return repository.existsByDocumentAndActiveAndManageAccounts(document, active, true);
    }

    public void save(Person person) {
        repository.save(person.toDatabase());
    }

    public Person getPerson(String document) {
        return repository.getByDocument(document)
                .map(PersonDatabase::toDomain)
                .orElseThrow(() -> new PersonNotFoundException(String.format("Person with document %s not found", document)));
    }

    public void verifyIfManagerIsAuthorized(String managerDocument) {
        var managerIsActive = existsByDocumentAndActive(managerDocument, true);

        if (!managerIsActive) {
            throw new ManagerNotAuthorizedException(String.format("Manager %s with insufficient resources", managerDocument));
        }
    }
}
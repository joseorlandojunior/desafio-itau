package com.jose.junior.desafio_itau.person.useCase.impl;

import com.jose.junior.desafio_itau.person.exception.ManagerNotAuthorizedException;
import com.jose.junior.desafio_itau.person.exception.PersonNotFoundException;
import com.jose.junior.desafio_itau.person.gateway.database.PersonRepository;
import com.jose.junior.desafio_itau.person.model.domain.Person;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repository;

    public Boolean existsByDocumentAndActive(String document, Boolean active) {
        return repository.existsByDocumentAndActiveAndManageAccounts(document, active, true);
    }

    public Boolean existsByDocument(String document) {
        return repository.existsByDocument(document);
    }

    public void save(Person person, Boolean includeAccount) {
        repository.save(person.toDatabase(includeAccount));
    }

    public Person getPerson(String document) {
        return repository.getByDocument(document)
                .map(person -> person.toDomain(true))
                .orElseThrow(() -> new PersonNotFoundException(String.format("Person with document %s not found", document)));
    }

    public void verifyIfManagerIsAuthorized(String managerDocument) {
        var managerIsActive = existsByDocumentAndActive(managerDocument, true);

        if (!managerIsActive) {
            throw new ManagerNotAuthorizedException(String.format("Manager %s with insufficient resources", managerDocument));
        }
    }

    public Boolean existsManager() {
        return repository.existsByActiveAndManageAccounts(true, true);
    }
}
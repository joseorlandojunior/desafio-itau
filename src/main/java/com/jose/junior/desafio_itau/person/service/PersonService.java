package com.jose.junior.desafio_itau.person.service;

import com.jose.junior.desafio_itau.person.gateway.database.PersonRepository;
import com.jose.junior.desafio_itau.person.model.domain.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository repository;

    public Boolean existsByDocumentAndActive(String document, Boolean active) {
        return repository.existsByDocumentAndActive(document, active);
    }

    public void save(Person person) {
        repository.save(person);
    }
}
package com.jose.junior.desafio_itau.person.gateway.database;

import com.jose.junior.desafio_itau.person.model.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    Boolean existsByDocumentAndActive(String document, Boolean active);
}

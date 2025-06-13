package com.jose.junior.desafio_itau.person.gateway.database;

import com.jose.junior.desafio_itau.person.model.database.PersonDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<PersonDatabase, Integer> {

    Boolean existsByDocumentAndActiveAndManageAccounts(String document, Boolean active, Boolean manageAccounts);

    Optional<PersonDatabase> getByDocument(String document);

    Boolean existsByDocument(String document);

    Boolean existsByActiveAndManageAccounts(Boolean active, Boolean manageAccounts);
}

package com.jose.junior.desafio_itau.person.useCase.impl;

import com.jose.junior.desafio_itau.person.model.domain.Person;
import com.jose.junior.desafio_itau.person.useCase.CreatePersonUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePersonUseCaseImpl implements CreatePersonUseCase {

    private final PersonService service;

    @Override
    public void execute(CreatePersonCommand cmd) {
        service.verifyIfManagerIsAuthorized(cmd.getManagerDocument());
        service.save(buildPerson(cmd));
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

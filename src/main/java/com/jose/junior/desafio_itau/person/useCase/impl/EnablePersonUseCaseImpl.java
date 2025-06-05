package com.jose.junior.desafio_itau.person.useCase.impl;

import com.jose.junior.desafio_itau.person.useCase.EnablePersonUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnablePersonUseCaseImpl implements EnablePersonUseCase {

    private final PersonService service;

    @Override
    public void execute(EnablePersonCommand cmd) {
        service.verifyIfManagerIsAuthorized(cmd.getManagerDocument());

        var person = service.getPerson(cmd.getPersonDocument());
        person.enable();
        service.save(person);
    }
}

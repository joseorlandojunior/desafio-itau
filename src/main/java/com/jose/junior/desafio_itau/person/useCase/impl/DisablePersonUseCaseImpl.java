package com.jose.junior.desafio_itau.person.useCase.impl;

import com.jose.junior.desafio_itau.person.useCase.DisablePersonUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DisablePersonUseCaseImpl implements DisablePersonUseCase {

    private final PersonService service;

    @Override
    public void execute(DisablePersonCommand cmd) {
        service.verifyIfManagerIsAuthorized(cmd.getManagerDocument());

        var person = service.getPerson(cmd.getPersonDocument());
        person.disable();
        service.save(person);
    }
}

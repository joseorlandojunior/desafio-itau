package com.jose.junior.desafio_itau.person.useCase.impl;

import com.jose.junior.desafio_itau.person.useCase.DisablePersonUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DisablePersonUseCaseImpl implements DisablePersonUseCase {

    private final PersonService service;

    @Override
    public void execute(DisablePersonCommand cmd) {
        var transactionInfoLog = String.format("DisablePersonUseCaseImpl_" + cmd.getPersonDocument());

        log.info("{} Payload received is {}", transactionInfoLog, cmd);
        service.verifyIfManagerIsAuthorized(cmd.getManagerDocument());

        var person = service.getPerson(cmd.getPersonDocument());
        person.disable();
        service.save(person, false);
        log.info("{} Person has been disabled.", transactionInfoLog);
    }
}

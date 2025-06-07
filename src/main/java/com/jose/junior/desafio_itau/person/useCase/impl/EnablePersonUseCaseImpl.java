package com.jose.junior.desafio_itau.person.useCase.impl;

import com.jose.junior.desafio_itau.person.useCase.EnablePersonUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnablePersonUseCaseImpl implements EnablePersonUseCase {

    private final PersonService service;

    @Override
    public void execute(EnablePersonCommand cmd) {
        var transactionInfoLog = String.format("EnablePersonUseCaseImpl_" + cmd.getPersonDocument());

        log.info("{} Payload received is {}", transactionInfoLog, cmd);
        service.verifyIfManagerIsAuthorized(cmd.getManagerDocument());

        var person = service.getPerson(cmd.getPersonDocument());
        person.enable();
        service.save(person, false);
        log.info("{} Person has been enabled.", transactionInfoLog);
    }
}

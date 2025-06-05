package com.jose.junior.desafio_itau.person.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ManagerNotAuthorizedException extends RuntimeException {
    public ManagerNotAuthorizedException(String message) {
        super(message);
    }
}

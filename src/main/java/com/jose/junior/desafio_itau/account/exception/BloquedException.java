package com.jose.junior.desafio_itau.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class BloquedException extends RuntimeException{

    public BloquedException(String message) {
        super(message);
    }
}

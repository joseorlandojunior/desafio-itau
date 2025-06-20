package com.jose.junior.desafio_itau.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValueForOperationInvalidException extends RuntimeException {
    public ValueForOperationInvalidException(String message) {
        super(message);
    }
}

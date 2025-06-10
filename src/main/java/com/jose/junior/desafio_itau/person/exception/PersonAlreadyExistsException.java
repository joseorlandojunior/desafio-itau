package com.jose.junior.desafio_itau.person.exception;

public class PersonAlreadyExistsException extends RuntimeException{
    public PersonAlreadyExistsException(String message) {
        super(message);
    }
}

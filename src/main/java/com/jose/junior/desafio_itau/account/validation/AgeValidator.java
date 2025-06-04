package com.jose.junior.desafio_itau.account.validation;

import com.jose.junior.desafio_itau.account.exception.BloquedException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class AgeValidator implements ConstraintValidator<AgeValid, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {

        int period = Period.between(value, LocalDate.now()).getYears();

        if (period < 18){
            throw new BloquedException("invalidated age.");
        }
        return true;
    }
}

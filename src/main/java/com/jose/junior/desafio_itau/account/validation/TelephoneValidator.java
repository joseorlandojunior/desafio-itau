package com.jose.junior.desafio_itau.account.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelephoneValidator implements ConstraintValidator<TelephoneBR, String> {

    private static final String TELEPHONE_REGEX = "^\\d{11}$";

    @Override
    public boolean isValid(String telephone, ConstraintValidatorContext context) {
        if (telephone == null) return false;
        return telephone.matches(TELEPHONE_REGEX);
    }
}

package com.application.config.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ValidationException;

@Component
public class FbrValidateValidator {

    private static Validator validator;

    @Autowired
    public void setValidator(Validator validator) {
        FbrValidateValidator.validator = validator;
    }

    public static void validate(Object object) {
        Errors errors = new BeanPropertyBindingResult(object, object.getClass().getSimpleName());
        validator.validate(object, errors);

        if (errors.hasErrors())
            throw new ValidationException(FbrValidateUtils.parserErros(errors));
    }
}

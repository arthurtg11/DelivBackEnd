package com.application.config.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;


public class ValidatorDateImpl implements ConstraintValidator<ValidatorDate, Date> {
    private ValidatorDate validatorDate;

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (validateRequired(value, context))
            return false;

        return true;
    }

    private boolean validateRequired(Date value, ConstraintValidatorContext context) {
        var err = this.validatorDate.required() && value == null;
        if (err) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Value is Required").addConstraintViolation();
        }
        return err;
    }

    @Override
    public void initialize(ValidatorDate constraintAnnotation) {
        this.validatorDate = constraintAnnotation;
    }
}
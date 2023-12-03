package com.application.config.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;


public class ValidatorStringImpl implements ConstraintValidator<ValidatorString, String> {
private  ValidatorString validatorString;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (validateRequired(value, context) || validateMinSize(value, context) || validateMaxSize(value, context))
            return false;

        return true;
    }

    private boolean validateRequired(String value, ConstraintValidatorContext context) {
        var err =  this.validatorString.required() && StringUtils.isBlank(value);
        if (err) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Value is Required").addConstraintViolation();
        }
        return err;
    }

    private boolean validateMinSize(String value, ConstraintValidatorContext context) {
        var err = StringUtils.length(value) < this.validatorString.minSize();
        if (err) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid Min Size, limit: " +  this.validatorString.minSize()).addConstraintViolation();
        }
        return err;
    }

    private boolean validateMaxSize(String value, ConstraintValidatorContext context) {
        var err = StringUtils.length(value) > this.validatorString.maxSize();
        if (err) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid Max Size, limit: " +  this.validatorString.maxSize()).addConstraintViolation();
        }
        return err;
    }

    @Override
    public void initialize(ValidatorString constraintAnnotation) {
        this.validatorString = constraintAnnotation;
    }
}
package com.application.config.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;


public class ValidatorLongImpl implements ConstraintValidator<ValidatorLong, Long> {
    @Autowired
    private HttpServletRequest request;

    private ValidatorLong validatorLong;

    private boolean validateRequired(Long value, ConstraintValidatorContext context) {
        var err = this.validatorLong.required() && value == null && !this.validatorLong.requiredOnUpdate();

        if (err) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Value is Required").addConstraintViolation();
        }
        return err;
    }

    private boolean validateRequiredOnUpdate(Long value, ConstraintValidatorContext context) {
        var err = this.validatorLong.requiredOnUpdate() && value == null && request.getMethod().equals(RequestMethod.PUT.name());

        if (err) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Value is Required").addConstraintViolation();
        }
        return err;
    }

    private boolean validateNegative(Long value, ConstraintValidatorContext context) {
        var err = !this.validatorLong.negative() && value != null && value < 0L;
        if (err) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Value is Negative").addConstraintViolation();
        }
        return err;
    }

    private boolean validatePrecision(Long value, ConstraintValidatorContext context) {
        var err = value != null && this.validatorLong.precision() < new BigDecimal(value).precision();
        if (err) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid Precision, limit " + this.validatorLong.precision()).addConstraintViolation();
        }
        return err;
    }

    @Override
    public void initialize(ValidatorLong constraintAnnotation) {
        this.validatorLong = constraintAnnotation;
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (validateRequired(value, context) || validateRequiredOnUpdate(value, context) || validateNegative(value, context) || validatePrecision(value, context))
            return false;

        return true;
    }
}
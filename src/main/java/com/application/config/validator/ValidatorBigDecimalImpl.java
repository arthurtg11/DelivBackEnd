package com.application.config.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;


public class ValidatorBigDecimalImpl implements ConstraintValidator<ValidatorBigDecimal, BigDecimal> {
    private ValidatorBigDecimal validatorBigDecimal;

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (validateRequired(value, context) || validateNegative(value, context) || validateScale(value, context) || validatePrecision(value, context))
            return false;

        return true;
    }

    private boolean validateRequired(BigDecimal value, ConstraintValidatorContext context) {
        var err = this.validatorBigDecimal.required() && value == null;
        if (err) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Value is Required").addConstraintViolation();
        }
        return err;
    }

    private boolean validateNegative(BigDecimal value, ConstraintValidatorContext context) {
        var err = !this.validatorBigDecimal.negative() && value != null && value.compareTo(BigDecimal.ZERO) < 0;
        if (err) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Value is Negative").addConstraintViolation();
        }
        return err;
    }

    private boolean validateScale(BigDecimal value, ConstraintValidatorContext context) {
        var err = value != null && this.validatorBigDecimal.scale() < value.scale();
        if (err) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid Scale, limit " + this.validatorBigDecimal.scale()).addConstraintViolation();
        }
        return err;
    }

    private boolean validatePrecision(BigDecimal value, ConstraintValidatorContext context) {
        var err = value != null && this.validatorBigDecimal.precision() < value.precision();
        if (err) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid Precision, limit " + this.validatorBigDecimal.precision()).addConstraintViolation();
        }
        return err;
    }

    @Override
    public void initialize(ValidatorBigDecimal constraintAnnotation) {
        this.validatorBigDecimal = constraintAnnotation;
    }
}
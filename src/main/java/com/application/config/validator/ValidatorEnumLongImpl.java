package com.application.config.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;


public class ValidatorEnumLongImpl implements ConstraintValidator<ValidatorEnum, Long> {
    List<Long> valueList = null;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (validateValue(value, context))
            return false;

        return true;
    }

    private boolean validateValue(Long value, ConstraintValidatorContext context) {
        var err =  !valueList.contains(value);
        if (err) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid Value").addConstraintViolation();
        }
        return err;
    }

    @Override
    public void initialize(ValidatorEnum constraintAnnotation) {
        valueList = new ArrayList<Long>();
        Class<? extends Enum<?>> enumBase = constraintAnnotation.enumBase();

        Enum[] enumValArr = enumBase.getEnumConstants();

        for (Enum enumVal : enumValArr) {
            var val = (ValidatorEnumInterface) enumVal;
            valueList.add(val.getValue());
        }
    }
}
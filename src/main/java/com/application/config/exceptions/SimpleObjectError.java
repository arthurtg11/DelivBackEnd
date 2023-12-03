package com.application.config.exceptions;


import lombok.Getter;
import lombok.ToString;
import org.springframework.context.MessageSource;

import javax.validation.ConstraintViolation;
import java.util.Locale;

@Getter
@ToString
class SimpleObjectError {
    String defaultMessage;
    String clazzName;
    String field;
    Object rejectedValue;
    String code;

    public static SimpleObjectError from(ConstraintViolation<?> violation, MessageSource msgSrc, Locale locale) {
        SimpleObjectError result = new SimpleObjectError();
        result.defaultMessage = msgSrc.getMessage(violation.getMessageTemplate(),
                new Object[]{violation.getLeafBean().getClass().getSimpleName(), violation.getPropertyPath().toString(),
                        violation.getInvalidValue()}, violation.getMessage(), locale);
        result.clazzName = violation.getLeafBean().getClass().getSimpleName();

        String field = null;
        for (var i : violation.getPropertyPath()) {
            field = i.toString();
        }

        result.field = field;
        result.rejectedValue = violation.getInvalidValue();
        result.code = violation.getMessageTemplate();
        return result;
    }
}

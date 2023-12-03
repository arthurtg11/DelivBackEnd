package com.application.config.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    public @ResponseBody Map<String, Object> handleNullPointerException(NullPointerException e) {
        log.error(e.getMessage(), e);
        final Map<String, Object> result = new LinkedHashMap<>();
        result.put("error", e.getMessage());
        return result;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public @ResponseBody Map<String, Object> handleException(Exception e) {
        log.error(e.getMessage(), e);
        final Map<String, Object> result = new LinkedHashMap<>();
        result.put("error", "Ocorreu um erro interno no servidor.");
        result.put("message", e.getMessage());
        return result;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FindByIDNotFoundException.class)
    public @ResponseBody Map<String, Object> handleFindByIDNotFoundException(FindByIDNotFoundException e) {
        log.error(e.getMessage(), e);
        final Map<String, Object> result = new LinkedHashMap<>();
        result.put("error", e.getMessage());
        return result;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TooManyRollsException.class)
    public @ResponseBody Map<String, Object> handleTooManyRollsException(TooManyRollsException e) {
        log.error(e.getMessage(), e);
        final Map<String, Object> result = new LinkedHashMap<>();
        result.put("error", e.getMessage());
        return result;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FbrValidatorException.class)
    public @ResponseBody Map<String, Object> handleFbrValidatorException(FbrValidatorException e) {
        log.error(e.getMessage(), e);
        final Map<String, Object> result = new LinkedHashMap<>();
        result.put("error", e.getMessage());
        return result;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public @ResponseBody Map<String, Object> handleValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        final Map<String, Object> result = new LinkedHashMap<>();
        result.put("error", e.getMessage());
        return result;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public @ResponseBody Map<String, Object> handleConstraintViolation(ConstraintViolationException e, ServletWebRequest request) {
        log.error(e.getMessage(), e);
        final Map<String, Object> result = new LinkedHashMap<>();
        result.put("message", e.getMessage());
        result.put("errors", e.getConstraintViolations().stream().map(cv -> SimpleObjectError.from(cv, messageSource, request.getLocale())));
        return result;
    }
}

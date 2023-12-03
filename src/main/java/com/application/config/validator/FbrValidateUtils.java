package com.application.config.validator;

import com.application.config.FbrUtils;
import org.springframework.validation.Errors;

import java.util.stream.Collectors;

public class FbrValidateUtils {
    public static void addErros(Errors errors, String message) {
        errors.reject(message);
    }

    public static void addErros(Errors errors, String message, Object... params) {
        addErros(errors, FbrUtils.format(message, params));
    }

    public static String parserErros(Errors errors) {
        if (!errors.hasErrors())
            return "";

        String result = errors.getAllErrors().stream().map(
                l -> l.getCodes()[l.getCodes().length - 1]
        ).collect(Collectors.joining("\n"));

        return result;
    }
}

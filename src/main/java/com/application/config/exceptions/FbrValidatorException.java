package com.application.config.exceptions;

import com.application.config.FbrUtils;

public class FbrValidatorException extends Exception {

    public FbrValidatorException(String message) {
        super(message);
    }

    public FbrValidatorException(String message, Object... params) {
        super(FbrUtils.format(message, params));
    }
}

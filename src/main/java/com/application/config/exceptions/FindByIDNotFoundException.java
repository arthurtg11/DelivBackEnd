package com.application.config.exceptions;

public class FindByIDNotFoundException extends Exception {

    public FindByIDNotFoundException() {
        super("FindById Not Found!");
    }
}

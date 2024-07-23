package com.molveno.restaurantReservation.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserValidationException extends RuntimeException {

    // this file is used to create custom exceptions for user validation
    // create your own exception class that extends RuntimeException if you want to have a custom exception error message
    private String field;
    public UserValidationException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}

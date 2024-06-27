package com.molveno.restaurantReservation.utils;

public class TableValidationException extends RuntimeException {

    // this file is used to create custom exceptions for table validation
    // create your own exception class that extends RuntimeException if you want to have a custom exception error message
    private String field;
    public TableValidationException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}

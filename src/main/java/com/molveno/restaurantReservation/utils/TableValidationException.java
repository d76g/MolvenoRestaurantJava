package com.molveno.restaurantReservation.utils;

public class TableValidationException extends RuntimeException {

    private String field;
    public TableValidationException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}

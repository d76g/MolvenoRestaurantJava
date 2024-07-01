package com.molveno.restaurantReservation.utils;

public class MenuValidationException extends RuntimeException {
    private String field;

    public MenuValidationException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }

}

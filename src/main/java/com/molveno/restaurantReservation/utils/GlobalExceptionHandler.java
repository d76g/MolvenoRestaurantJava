package com.molveno.restaurantReservation.utils;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<Map<String, String>> handleUserValidationException(UserValidationException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("field", ex.getField());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

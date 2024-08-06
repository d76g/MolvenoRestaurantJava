package com.molveno.restaurantReservation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Properties;

@RestController
public class LocalizationController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/api/messages")
    public Properties getMessages(@RequestParam(defaultValue = "en") String lang) {
        Locale locale = new Locale(lang);
        Properties properties = new Properties();
        properties.put("Check-In", messageSource.getMessage("Check-In", null, locale));
        properties.put("Cancel", messageSource.getMessage("Cancel", null, locale));
        properties.put("View-Details", messageSource.getMessage("View-Details", null, locale));
        properties.put("No-Reservations-for-today", messageSource.getMessage("No-Reservations-for-today", null, locale));
        properties.put("Are-you-sure", messageSource.getMessage("Are-you-sure", null, locale));
        properties.put("Yes", messageSource.getMessage("Yes", null, locale));
        properties.put("No", messageSource.getMessage("No", null, locale));
        properties.put("You-wont-be-able-to-revert-this", messageSource.getMessage("You-wont-be-able-to-revert-this", null, locale));
        properties.put("Yes-cancel-it", messageSource.getMessage("Yes-cancel-it", null, locale));
        properties.put("Yes-check-in", messageSource.getMessage("Yes-check-in", null, locale));
        properties.put("Checked-in", messageSource.getMessage("Checked-in", null, locale));
        properties.put("The-customer-has-been-checked-in", messageSource.getMessage("The-customer-has-been-checked-in", null, locale));
        properties.put("ok", messageSource.getMessage("ok", null, locale));
        properties.put("Take-order", messageSource.getMessage("Take-order", null, locale));
        properties.put("Order", messageSource.getMessage("Order", null, locale));
        properties.put("Close", messageSource.getMessage("Close", null, locale));
        properties.put("Total-price", messageSource.getMessage("Total-price", null, locale));
        properties.put("Add", messageSource.getMessage("Add", null, locale));
        properties.put("Order-Placed-successfully", messageSource.getMessage("Order-Placed-successfully", null, locale));
        properties.put("Pay-Bill", messageSource.getMessage("Pay-Bill", null, locale));
        properties.put("Order-Details", messageSource.getMessage("Order-Details", null, locale));
        properties.put("Item-Name", messageSource.getMessage("Item-Name", null, locale));
        properties.put("Quantity", messageSource.getMessage("Quantity", null, locale));
        properties.put("Item-Price", messageSource.getMessage("Item-Price", null, locale));
        properties.put("Make-Payment", messageSource.getMessage("Make-Payment", null, locale));
        properties.put("Pending-Order", messageSource.getMessage("Pending-Order", null, locale));
        properties.put("Order-not-placed-yet", messageSource.getMessage("Order-not-placed-yet", null, locale));
        properties.put("Table-NO", messageSource.getMessage("Table-NO", null, locale));
        properties.put("Paid", messageSource.getMessage("Paid", null, locale));
        properties.put("Hotel-Guest", messageSource.getMessage("Hotel-Guest", null, locale));
        properties.put("Room-NO", messageSource.getMessage("Room-NO", null, locale));
        properties.put("Cancelled", messageSource.getMessage("Cancelled", null, locale));
        properties.put("Reservation-Cancelled", messageSource.getMessage("Reservation-Cancelled", null, locale));
        properties.put("Reservation", messageSource.getMessage("Reservation", null, locale));
        properties.put("Reservations", messageSource.getMessage("Reservations", null, locale));
        properties.put("Price", messageSource.getMessage("Price", null, locale));
        properties.put("Total", messageSource.getMessage("Total", null, locale));
        properties.put("Confirm", messageSource.getMessage("Confirm", null, locale));
        properties.put("Order-Failed", messageSource.getMessage("Order-Failed", null, locale));
        properties.put("Please-add-items-to-order", messageSource.getMessage("Please-add-items-to-order", null, locale));
        properties.put("Order-Placed-Successfully", messageSource.getMessage("Order-Placed-Successfully", null, locale));
        properties.put("Order-has-been-sent-to-kitchen", messageSource.getMessage("Order-has-been-sent-to-kitchen", null, locale));
        properties.put("Yes-delete-it", messageSource.getMessage("Yes-delete-it", null, locale));
        properties.put("Opps", messageSource.getMessage("Opps", null, locale));
        properties.put("Cannot-delete-this-order", messageSource.getMessage("Cannot-delete-this-order", null, locale));
        properties.put("Order-deleted", messageSource.getMessage("Order-deleted", null, locale));
        properties.put("Order-is-already-paid", messageSource.getMessage("Order-is-already-paid", null, locale));
        properties.put("Something-went-wrong", messageSource.getMessage("Something-went-wrong", null, locale));
        properties.put("PAID", messageSource.getMessage("PAID", null, locale));
        properties.put("PLACED", messageSource.getMessage("PLACED", null, locale));
        properties.put("CANCELLED", messageSource.getMessage("CANCELLED", null, locale));
        properties.put("PENDING", messageSource.getMessage("PENDING", null, locale));
        properties.put("CONFIRMED", messageSource.getMessage("CONFIRMED", null, locale));
        properties.put("ATTENDED", messageSource.getMessage("ATTENDED", null, locale));
        properties.put("Order-sent-to-hotel-room", messageSource.getMessage("Order-sent-to-hotel-room", null, locale));
        properties.put("Payment-processed", messageSource.getMessage("Payment-processed", null, locale));
        properties.put("Reservation-Deleted", messageSource.getMessage("Reservation-Deleted", null, locale));
        properties.put("Reservation-has-been-deleted", messageSource.getMessage("Reservation-has-been-deleted", null, locale));
        properties.put("Reservation-Status-Updated", messageSource.getMessage("Reservation-Status-Updated", null, locale));
        properties.put("Reservation-status-has-been-updated", messageSource.getMessage("Reservation-status-has-been-updated", null, locale));
        properties.put("Reservation-Saved", messageSource.getMessage("Reservation-Saved", null, locale));
        properties.put("Reservation-has-been-saved", messageSource.getMessage("Reservation-has-been-saved", null, locale));
        properties.put("Reservation-Updated", messageSource.getMessage("Reservation-Updated", null, locale));
        properties.put("Reservation-has-been-updated", messageSource.getMessage("Reservation-has-been-updated", null, locale));
        properties.put("No-enough-tables", messageSource.getMessage("No-enough-tables", null, locale));
        properties.put("zero-guests", messageSource.getMessage("zero-guests", null, locale));
        properties.put("empty-date-time", messageSource.getMessage("empty-date-time", null, locale));
        properties.put("Guests", messageSource.getMessage("Guests", null, locale));
        properties.put("cant-modify-cancelled-reservation", messageSource.getMessage("cant-modify-cancelled-reservation", null, locale));
        properties.put("cant-modify-paid-reservation", messageSource.getMessage("cant-modify-paid-reservation", null, locale));
        properties.put("Payment-Info", messageSource.getMessage("Payment-Info", null, locale));
        properties.put("Please-pay-at-the-front-desk", messageSource.getMessage("Please-pay-at-the-front-desk", null, locale));
        properties.put("Yes-update-it", messageSource.getMessage("Yes-update-it", null, locale));
        properties.put("Are-you-sure-delete", messageSource.getMessage("Are-you-sure-delete", null, locale));
        properties.put("Menu-item-saved", messageSource.getMessage("Menu-item-saved", null, locale));
        properties.put("User-saved", messageSource.getMessage("User-saved", null, locale));
        properties.put("Password-message", messageSource.getMessage("Password-message", null, locale));
        properties.put("stock-saved-successfully", messageSource.getMessage("stock-saved-successfully", null, locale));
        properties.put("stock-category-save-successfully", messageSource.getMessage("stock-category-save-successfully", null, locale));
        properties.put("username-already-exists", messageSource.getMessage("username-already-exists", null, locale));
        properties.put("Email-already-exists", messageSource.getMessage("Email-already-exists", null, locale));

        properties.put("stock-not-enough", messageSource.getMessage("stock-not-enough", null, locale));
        properties.put("Stock-Alert", messageSource.getMessage("Stock-Alert", null, locale));
        properties.put("is-low-on-stock", messageSource.getMessage("is-low-on-stock", null, locale));
        properties.put("Order-Deleted", messageSource.getMessage("Order-Deleted", null, locale));
        properties.put("invalid-check-in-time", messageSource.getMessage("invalid-check-in-time", null, locale));
        properties.put("invalid-reservation-time", messageSource.getMessage("invalid-reservation-time", null, locale));
        properties.put("make-another-order", messageSource.getMessage("make-another-order", null, locale));
        properties.put("table-capacity-exceeded-max-seats-number", messageSource.getMessage("table-capacity-exceeded-max-seats-number", null, locale));
        properties.put("table-already-exists", messageSource.getMessage("table-already-exists", null, locale));
        properties.put("table-number-should-be-greater-than-zero", messageSource.getMessage("table-number-should-be-greater-than-zero", null, locale));
        properties.put("table-capacity-should-be-greater-than-zero", messageSource.getMessage("table-capacity-should-be-greater-than-zero", null, locale));
        properties.put("Order-is-already-placed", messageSource.getMessage("Order-is-already-placed", null, locale));
        properties.put("Order-is-already-cancelled", messageSource.getMessage("Order-is-already-cancelled", null, locale));
        properties.put("Order-Cancelled", messageSource.getMessage("Order-Cancelled", null, locale));
        return properties;

    }
}
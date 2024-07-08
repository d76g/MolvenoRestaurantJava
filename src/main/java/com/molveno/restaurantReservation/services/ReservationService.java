package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.Reservation;

import java.util.List;

public interface ReservationService {
    // list all reservations
    List<Reservation> listReservations();

    // get a reservation by id
    Reservation getReservation(Long id);

    // delete a reservation
    void deleteReservation(Long id);

    //TODO: CHECK FOR EMPTY FIELDS AND RETURN ERROR MESSAGE
    // create a new reservation
    Reservation createReservation(Reservation reservation);

    // update a reservation
    Reservation updateReservation(long id, Reservation reservation);

    Reservation updateReservationStatus(long id, String reservationStatus);

    Reservation getReservationById(long id);

    // get all reservation for today
    List<Reservation> getReservationsForToday();
}

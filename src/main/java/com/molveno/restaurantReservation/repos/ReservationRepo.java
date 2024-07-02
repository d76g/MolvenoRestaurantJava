package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    List<Reservation> findAll();
}

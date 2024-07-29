package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    List<Reservation> findAll();

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.reservationDate = :currentDate")
    int countReservationsForToday(@Param("currentDate") String currentDate);

    // total number of guests
    @Query("SELECT SUM(r.numberOfGuests) FROM Reservation r WHERE r.reservationStatus = 'PAID'")
    int countTotalGuests();
}

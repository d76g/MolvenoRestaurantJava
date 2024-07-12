package com.molveno.restaurantReservation.services;


import com.molveno.restaurantReservation.models.DTO.Mappers.ReservationMapper;
import com.molveno.restaurantReservation.models.DTO.Response.ReservationResponseDTO;
import com.molveno.restaurantReservation.models.Reservation;
import com.molveno.restaurantReservation.models.Table;
import com.molveno.restaurantReservation.repos.ReservationRepo;
import com.molveno.restaurantReservation.repos.TableRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImp implements ReservationService{

    private final ReservationRepo reservationRepo;

    private final TableRepo tableRepo;
    public ReservationServiceImp(ReservationRepo reservationRepo, TableRepo tableRepo) {
        this.reservationRepo = reservationRepo;
        this.tableRepo = tableRepo;
    }
    // list all reservations
    @Override
    public List<ReservationResponseDTO> listReservations() {
        return reservationRepo.findAll().stream()
                .map(ReservationMapper::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    // get a reservation by id
    @Override
    public ReservationResponseDTO getReservation(Long id) {
        Reservation reservation = reservationRepo.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));
        return ReservationMapper.mapToResponseDTO(reservation);
    }
    @Override
    // delete a reservation
    public void deleteReservation(Long id) {
        reservationRepo.deleteById(id);
    }
    //TODO: CHECK FOR EMPTY FIELDS AND RETURN ERROR MESSAGE

    // get reservation by id
    @Override
    public Reservation getReservationById(long id) {
        return reservationRepo.findById(id).orElse(null);
    }

    @Override
    public List<ReservationResponseDTO> getReservationsForToday() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = LocalDateTime.now().format(formatter);
        return reservationRepo.findAll().stream()
                .filter(reservation -> reservation.getReservationDate().equals(today) && !reservation.getReservationStatus().equals("CANCELLED") && !reservation.getReservationStatus().equals("PAID"))
                .map(ReservationMapper::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // create a new reservation
    @Override
    public Reservation createReservation(Reservation reservation) {
       // check if there are available tables for the reservation at the time and date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime reservationStart = LocalDateTime.parse(reservation.getReservationDate() + " " + reservation.getReservationTime(), formatter);
        LocalDateTime reservationEnd = reservationStart.plusHours(3);

        // Get all confirmed reservations that overlap with the new reservation time
        List<Reservation> conflictingReservations = reservationRepo.findAll().stream()
                .filter(existingReservation -> existingReservation.getReservationStatus().equals("CONFIRMED"))
                .filter(existingReservation -> {
                    LocalDateTime existingStart = LocalDateTime.parse(existingReservation.getReservationDate() + " " + existingReservation.getReservationTime(), formatter);
                    LocalDateTime existingEnd = existingStart.plusHours(3);
                    return (existingStart.isBefore(reservationEnd) && existingEnd.isAfter(reservationStart));
                })
                .collect(Collectors.toList());

        // Find tables that are free during the new reservation time
        List<Table> availableTables = tableRepo.findAll();
        for (Reservation existingReservation : conflictingReservations) {
            availableTables.removeAll(existingReservation.getTables());
        }
        // Find the best fit tables for the number of guests
        Set<Table> assignedTables = findBestFitTables(availableTables, reservation.getNumberOfGuests());
        if (assignedTables.isEmpty()) {
            throw new RuntimeException("Not enough available tables for the reservation time and date");
        }
        reservation.setTables(assignedTables);
        reservation.setReservationStatus("CONFIRMED");
        return reservationRepo.save(reservation);
    }
    // update a reservation
    @Override
    public Reservation updateReservation(long id, Reservation reservation) {
        if ("CANCELLED".equals(reservation.getReservationStatus()) || "PAID".equals(reservation.getReservationStatus())){
            throw new IllegalStateException("Cannot modify this reservation, it has already been cancelled or already attended and paid.");
        }

        // Fetch the existing reservation from the database
        Reservation existingReservation = reservationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime reservationStart = LocalDateTime.parse(reservation.getReservationDate() + " " + reservation.getReservationTime(), formatter);
        LocalDateTime reservationEnd = reservationStart.plusHours(3);

        // Check if the number of guests has changed
        if (reservation.getNumberOfGuests() != existingReservation.getNumberOfGuests()) {
            // Get all confirmed reservations that overlap with the new reservation time
            List<Reservation> conflictingReservations = reservationRepo.findAll().stream()
                    .filter(conflictReservation -> conflictReservation.getReservationStatus().equals("CONFIRMED"))
                    .filter(conflictReservation -> {
                        LocalDateTime existingStart = LocalDateTime.parse(conflictReservation.getReservationDate() + " " + conflictReservation.getReservationTime(), formatter);
                        LocalDateTime existingEnd = existingStart.plusHours(3);
                        return (existingStart.isBefore(reservationEnd) && existingEnd.isAfter(reservationStart));
                    })
                    .collect(Collectors.toList());

            // Find tables that are free during the new reservation time
            List<Table> availableTables = tableRepo.findAll();
            for (Reservation conflictReservation : conflictingReservations) {
                availableTables.removeAll(conflictReservation.getTables());
            }

            // Find the best fit tables for the number of guests
            Set<Table> assignedTables = findBestFitTables(availableTables, reservation.getNumberOfGuests());
            if (assignedTables.isEmpty()) {
                throw new RuntimeException("Not enough available tables for the reservation time and date");
            }

            reservation.setTables(assignedTables);
        } else {
            // If the number of guests has not changed, retain the existing tables
            reservation.setTables(existingReservation.getTables());
        }

        // Update other reservation details
        reservation.setReservationStatus(reservation.getReservationStatus());
        assignTablesToReservation(reservation);
        reservationRepo.save(reservation);
        return existingReservation;
    }

    @Override
    public Reservation updateReservationStatus(long id, String reservationStatus) {
        Reservation existingReservation = reservationRepo.findById(id).orElse(null);
        if (existingReservation == null) {
            throw new RuntimeException("Reservation not found");
        }
        if ("CANCELLED".equals(existingReservation.getReservationStatus()) && !"CANCELLED".equals(reservationStatus)) {
            throw new IllegalStateException("Cannot modify a CANCELLED reservation.");
        }
        if ("PAID".equals(existingReservation.getReservationStatus()) && !"PAID".equals(reservationStatus)) {
            throw new IllegalStateException("Cannot modify an PAID reservation.");
        }
        if (reservationStatus.equals("CANCELLED")) {
            existingReservation.setTables(new HashSet<>());
        }
        existingReservation.setReservationStatus(reservationStatus);
        reservationRepo.save(existingReservation);
        return existingReservation;
    }
    // assign tables to a reservation
    public void assignTablesToReservation(Reservation reservation) {
        int numberOfGuests = reservation.getNumberOfGuests();
        // get all tables
        List<Table> availableTables = tableRepo.findAll();

        Set<Table> assignedTables = findBestFitTables(availableTables, numberOfGuests);

        if(assignedTables.isEmpty()) {
            throw new RuntimeException("No tables available for the number of guests");
        }
        // set the assigned tables to the reservation
        reservation.setTables(assignedTables);
    }
    private Set<Table> findBestFitTables(List<Table> availableTables, int numberOfGuests) {
        // Sort available tables by capacity in descending order
        availableTables.sort(Comparator.comparingInt(Table::getTableCapacity).reversed());

        Set<Table> assignedTables = new HashSet<>();
        int totalCapacity = 0;

        // First, try to find an exact match
        for (Table table : availableTables) {
            if (table.getTableCapacity() == numberOfGuests) {
                assignedTables.add(table);
                return assignedTables;
            }
        }

        // If no exact match, try to find the best fit with a combination of tables
        for (Table table : availableTables) {
            if (totalCapacity >= numberOfGuests) {
                break;
            }
            assignedTables.add(table);
            totalCapacity += table.getTableCapacity();
        }

        // If the total capacity is not sufficient, clear the assigned tables and try combinations
        if (totalCapacity < numberOfGuests) {
            assignedTables.clear();
            totalCapacity = 0;

            // Use a combination of tables to fit the number of guests
            for (Table table : availableTables) {
                if (totalCapacity >= numberOfGuests) {
                    break;
                }
                assignedTables.add(table);
                totalCapacity += table.getTableCapacity();
            }

            // If it's still not enough, clear the assigned tables to indicate failure
            if (totalCapacity < numberOfGuests) {
                assignedTables.clear();
            }
        }

        return assignedTables;
    }



}

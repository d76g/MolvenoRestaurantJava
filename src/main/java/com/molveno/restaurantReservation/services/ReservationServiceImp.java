package com.molveno.restaurantReservation.services;


import com.molveno.restaurantReservation.models.CustomerOrder;
import com.molveno.restaurantReservation.models.DTO.Mappers.ReservationMapper;
import com.molveno.restaurantReservation.models.DTO.Response.ReservationResponseDTO;
import com.molveno.restaurantReservation.models.Reservation;
import com.molveno.restaurantReservation.models.Table;
import com.molveno.restaurantReservation.repos.OrderRepo;
import com.molveno.restaurantReservation.repos.ReservationRepo;
import com.molveno.restaurantReservation.repos.TableRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImp implements ReservationService{

    private final ReservationRepo reservationRepo;
    private final OrderRepo orderRepo;

    private final CustomerOrderService customerOrderService;

    private final TableRepo tableRepo;
    public ReservationServiceImp(ReservationRepo reservationRepo, OrderRepo orderRepo, CustomerOrderService customerOrderService, TableRepo tableRepo) {
        this.reservationRepo = reservationRepo;
        this.orderRepo = orderRepo;
        this.customerOrderService = customerOrderService;
        this.tableRepo = tableRepo;
    }
    // list all reservations
    @Override
    public List<ReservationResponseDTO> listReservations() {
        return reservationRepo.findAllOrderByStatusAndDateDesc().stream()
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
        if (reservation.getNumberOfGuests() == 0) {
            throw new RuntimeException("zero-guests");
        }
        if (reservation.getReservationDate().isEmpty() || reservation.getReservationTime().isEmpty()) {
            throw new RuntimeException("empty-date-time");
        }
        // check if the reservation time is in the past
        if(LocalDateTime.parse(reservation.getReservationDate() + " " + reservation.getReservationTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).isBefore(LocalDateTime.now())) {
            throw new RuntimeException("invalid-reservation-time");
        }
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
            throw new RuntimeException("No-enough-tables");
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
        // Compare the reservation times
        if (!Objects.equals(reservation.getReservationTime(), existingReservation.getReservationTime()) ||
                !Objects.equals(reservation.getReservationDate(), existingReservation.getReservationDate())) {

            // Parse the new reservation date and time
            LocalDateTime newReservationDateTime = LocalDateTime.parse(
                    reservation.getReservationDate() + " " + reservation.getReservationTime(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            );

            // Check if the new reservation time is in the past
            if (newReservationDateTime.isBefore(LocalDateTime.now())) {
                throw new RuntimeException("invalid-reservation-time");
            }
        }
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
                throw new RuntimeException("No-enough-tables");
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
    public void updateReservationStatus(long id, String reservationStatus) {
        Reservation existingReservation = reservationRepo.findById(id).orElse(null);
        if (existingReservation == null) {
            throw new RuntimeException("Reservation not found");
        }
        // check if the reservation date is in the past
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime reservationStart = LocalDateTime.parse(existingReservation.getReservationDate() + " " + existingReservation.getReservationTime(), formatter);
        if(reservationStatus.equals("ATTENDED")) {
            // ensure the check in must be 30 minutes before the reservation time and between the reservation time not after 3 hours of the reservation time
            LocalDateTime checkInTime = LocalDateTime.now();
            if (checkInTime.isBefore(reservationStart.minusMinutes(30)) || checkInTime.isAfter(reservationStart.plusHours(3))) {
                throw new RuntimeException("invalid-check-in-time");
            }
        }
        if (reservationStart.isBefore(LocalDateTime.now()) && "CANCELLED".equals(existingReservation.getReservationStatus()) && !"CANCELLED".equals(reservationStatus)) {
            throw new RuntimeException("cant-modify-cancelled-reservation");
        } else if ("PAID".equals(existingReservation.getReservationStatus()) && !"PAID".equals(reservationStatus)) {
            throw new RuntimeException("cant-modify-paid-reservation");
        } else if (reservationStatus.equals("CANCELLED")) {
            existingReservation.setTables(new HashSet<>());
        }

        existingReservation.setReservationStatus(reservationStatus);
        reservationRepo.save(existingReservation);
    }
    // assign tables to a reservation
    public void assignTablesToReservation(Reservation reservation) {
        int numberOfGuests = reservation.getNumberOfGuests();
        // get all tables
        List<Table> availableTables = tableRepo.findAll();

        Set<Table> assignedTables = findBestFitTables(availableTables, numberOfGuests);

        if(assignedTables.isEmpty()) {
            throw new RuntimeException("No-enough-tables");
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

        // If the number of guests is greater than 8, try to use tables with capacity of 2 and 4
        if (numberOfGuests > 8) {
            List<Table> preferredTables = new ArrayList<>();
            for (Table table : availableTables) {
                if (table.getTableCapacity() == 2 || table.getTableCapacity() == 4) {
                    preferredTables.add(table);
                }
            }

            // Sort preferred tables by capacity in descending order
            preferredTables.sort(Comparator.comparingInt(Table::getTableCapacity).reversed());

            // Use a combination of tables with capacities 2 and 4 to fit the number of guests
            for (Table table : preferredTables) {
                if (totalCapacity >= numberOfGuests) {
                    break;
                }
                assignedTables.add(table);
                totalCapacity += table.getTableCapacity();
            }

            // If the total capacity from preferred tables is not sufficient
            if (totalCapacity < numberOfGuests) {
                assignedTables.clear();
                totalCapacity = 0;

                // Try using any combination of tables
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
        } else {
            // For guests less than or equal to 8, use any available table combination
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

                // Use any combination of tables to fit the number of guests
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
        }

        return assignedTables;
    }



    // make payment for a reservation
    @Override
    public void makePayment(long reservationId){
        Reservation reservation = reservationRepo.findById(reservationId).orElse(null);
        if (reservation == null) {
            throw new RuntimeException("Reservation not found");
        }
        // change all the orders in the reservation to paid
        Iterable<CustomerOrder> orders = customerOrderService.findByReservationId(reservationId);
        for (CustomerOrder order : orders) {
            order.setStatus("PAID");
            orderRepo.save(order);
        }
        reservation.setReservationStatus("PAID");
    }


}

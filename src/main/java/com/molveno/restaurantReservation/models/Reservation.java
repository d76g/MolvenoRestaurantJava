package com.molveno.restaurantReservation.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private long reservation_id;
    private String customer_first_name;
    private String customer_last_name;
    private String customer_email;
    private String customer_phone;
    private String reservation_date;
    private String reservation_time;
    private int number_of_guests;
    private boolean is_guest;
    private String roomNumber;
    private String status;
    /*
    Reservation Entity:
    Contains a set of tables.
    Uses @ManyToMany with mappedBy to indicate that reservations is the inverse side of the relationship.
     */
    @ManyToMany(mappedBy = "reservations")
    private Set<Table> table = new HashSet<>();

    // Order Relationship
    @OneToMany(mappedBy = "reservation")
    private Set<CustomerOrder> orders;
    public long getReservation_id() {
        return reservation_id;
    }

    public String getCustomer_first_name() {
        return customer_first_name;
    }

    public void setCustomer_first_name(String customer_first_name) {
        this.customer_first_name = customer_first_name;
    }

    public String getCustomer_last_name() {
        return customer_last_name;
    }

    public void setCustomer_last_name(String customer_last_name) {
        this.customer_last_name = customer_last_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(String reservation_date) {
        this.reservation_date = reservation_date;
    }

    public String getReservation_time() {
        return reservation_time;
    }

    public void setReservation_time(String reservation_time) {
        this.reservation_time = reservation_time;
    }

    public int getNumber_of_guests() {
        return number_of_guests;
    }

    public void setNumber_of_guests(int number_of_guests) {
        this.number_of_guests = number_of_guests;
    }

    public boolean isIs_guest() {
        return is_guest;
    }

    public void setIs_guest(boolean is_guest) {
        this.is_guest = is_guest;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Set<Table> getTable() {
        return table;
    }
    public void setTable(Set<Table> table) {
        this.table = table;
    }
    public Set<CustomerOrder> getOrders() {
        return orders;
    }
    public void setOrders(Set<CustomerOrder> orders) {
        this.orders = orders;
    }
    public Reservation() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return reservation_id == that.reservation_id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(reservation_id);
    }

    public Reservation(long reservation_id, String customer_first_name, String customer_last_name, String customer_email, String customer_phone, String reservation_date, String reservation_time, int number_of_guests, boolean is_guest, String roomNumber, String status) {
        this.reservation_id = reservation_id;
        this.customer_first_name = customer_first_name;
        this.customer_last_name = customer_last_name;
        this.customer_email = customer_email;
        this.customer_phone = customer_phone;
        this.reservation_date = reservation_date;
        this.reservation_time = reservation_time;
        this.number_of_guests = number_of_guests;
        this.is_guest = is_guest;
        this.roomNumber = roomNumber;
        this.status = status;
    }
}

package com.molveno.restaurantReservation.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Reservation {

    @Id
    @GeneratedValue
    @Column(name = "reservation_id")
    private long id;
    @NotNull(message = "First name is required")
    @Length(min = 2, max = 50)
    private String customerFirstName;
    @NotNull(message = "Last name is required")
    @Length(min = 2, max = 50)
    private String customerLastName;
    @NotNull(message = "Email is required")
    @Length(min = 2, max = 50)
    @Email(message = "Email should be valid")
    private String customerEmail;
    @NotNull(message = "Phone number is required")
    @Length(min = 9, max = 20)
    private String customerPhone;
    @NotNull(message = "Reservation date is required")
    private String reservationDate;
    @NotNull(message = "Reservation time is required")
    private String reservationTime;
    @NotNull(message = "Number of guests is required")
    private int numberOfGuests;
    @NotNull(message = "Guest status is required")
    private boolean guest;
    @Column(nullable = true)
    @Length(min = 0, max = 6)
    private String roomNumber;
    @NotNull(message = "Reservation status is required")
    private String reservationStatus;
    /*
    Reservation Entity:
    Contains a set of tables.
    Uses @ManyToMany with mappedBy to indicate that reservations is the inverse side of the relationship.
     */
    @ManyToMany
    @JoinTable(
            name = "reserved_table",
            joinColumns = @JoinColumn(name = "table_id"),
            inverseJoinColumns = @JoinColumn(name = "reservation_id")
    )
    private Set<Table> tables = new HashSet<>();
    // Order Relationship
    @OneToMany(mappedBy = "reservation")
    private Set<CustomerOrder> orders;


    public Set<Table> getTables() {
        return tables;
    }
    public void setTables(Set<Table> tables) {
        this.tables = tables;
    }
    public Set<CustomerOrder> getOrders() {
        return orders;
    }
    public void setOrders(Set<CustomerOrder> orders) {
        this.orders = orders;
    }
    public Reservation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public boolean isGuest() {
        return guest;
    }

    public void setGuest(boolean guest) {
        this.guest = guest;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

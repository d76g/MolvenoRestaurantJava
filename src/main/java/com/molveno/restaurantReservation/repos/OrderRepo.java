package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.CustomerOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends CrudRepository<CustomerOrder, Long>{
    List<CustomerOrder> findAll();
    // find by reservation id
    CustomerOrder findByReservationId(long reservationId);
    List<CustomerOrder> findAllByReservationId(long reservationId);

    // findAllByReservationId where status in not equal to 'CANCELLED'
    @Query("SELECT o FROM CustomerOrder o WHERE o.reservation.id = ?1 AND o.status != 'CANCELLED'")
    List<CustomerOrder> findAllByReservationIdAndStatusNotCancelled(long reservationId);

}

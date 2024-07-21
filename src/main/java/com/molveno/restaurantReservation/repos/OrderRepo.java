package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.CustomerOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends CrudRepository<CustomerOrder, Long>{
    // find by reservation id
    CustomerOrder findByReservationId(long reservationId);
    List<CustomerOrder> findAllByReservationId(long reservationId);
}

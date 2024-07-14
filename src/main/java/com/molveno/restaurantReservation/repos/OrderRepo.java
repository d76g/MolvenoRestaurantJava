package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.CustomerOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends CrudRepository<CustomerOrder, Long>{
    // find all orders
}

package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.CustomerOrder;
import com.molveno.restaurantReservation.models.DTO.OrderDTO;

public interface CustomerOrderService {
    Iterable<CustomerOrder> findAll();
    CustomerOrder findById(long id);
    CustomerOrder save(OrderDTO customerOrder);
    void deleteById(long id);
    void deleteAll();
}

package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.CustomerOrder;
import com.molveno.restaurantReservation.models.DTO.OrderDTO;
import com.molveno.restaurantReservation.models.DTO.Response.OrderResponseDTO;

import java.util.List;

public interface CustomerOrderService {
    Iterable<CustomerOrder> findAll();

    List<OrderResponseDTO> mapToResponseDTO(Iterable<CustomerOrder> customerOrders);

    CustomerOrder findById(long id);
    CustomerOrder save(OrderDTO customerOrder);

    // change order status
    CustomerOrder changeOrderStatus(long id, String status);

    void deleteById(long id);
    void deleteAll();
}

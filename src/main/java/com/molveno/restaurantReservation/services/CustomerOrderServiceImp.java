package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.CustomerOrder;
import com.molveno.restaurantReservation.models.DTO.OrderDTO;

import com.molveno.restaurantReservation.models.DTO.Response.OrderItemResponseDTO;
import com.molveno.restaurantReservation.models.DTO.Response.OrderResponseDTO;
import com.molveno.restaurantReservation.models.Menu;
import com.molveno.restaurantReservation.models.OrderItem;
import com.molveno.restaurantReservation.repos.MenuRepo;
import com.molveno.restaurantReservation.repos.OrderRepo;
import com.molveno.restaurantReservation.repos.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerOrderServiceImp implements CustomerOrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ReservationRepo reservationRepo;
    @Autowired
    private MenuRepo menuRepo;

    @Override
    public Iterable<CustomerOrder> findAll() {
        return orderRepo.findAll();
    }

    @Override
    public CustomerOrder findById(long id) {
        return orderRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    @Override
    public CustomerOrder save(OrderDTO orderDTO) {
        // Retrieve the reservation and ensure it exists
        var reservationOpt = reservationRepo.findById(orderDTO.getReservationId());
        if (reservationOpt.isEmpty()) {
            throw new IllegalArgumentException("Reservation not found");
        }

        CustomerOrder newOrder = new CustomerOrder();
        newOrder.setReservation(reservationOpt.get());

        Set<OrderItem> orderItems = orderDTO.getOrderItems().stream().map(orderItemDTO -> {
            OrderItem newOrderItem = new OrderItem();
            newOrderItem.setOrder(newOrder);
            newOrderItem.setMenu(menuRepo.findById(orderItemDTO.getMenuId())
                    .orElseThrow(() -> new IllegalArgumentException("Menu item not found")));
            newOrderItem.setQuantity(orderItemDTO.getQuantity());
            return newOrderItem;
        }).collect(Collectors.toSet());
        newOrder.setOrderItem(orderItems);

        double totalPrice = orderItems.stream().mapToDouble(orderItem -> orderItem.getMenu().getPrice() * orderItem.getQuantity()).sum();
        newOrder.setTotal_price(totalPrice);
        return orderRepo.save(newOrder);
    }

    @Override
    public void deleteById(long id) {

    }

    public OrderResponseDTO mapToResponseDTO(CustomerOrder customerOrder){
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderId(customerOrder.getOrder_id());
        orderResponseDTO.setTotalPrice(customerOrder.getTotal_price());
        orderResponseDTO.setReservationId(customerOrder.getReservation().getId());

        // get menu id and search for menu item
        Menu menuItem = menuRepo.findById(customerOrder.getOrderItem().stream().findFirst().get().getMenu().getMenuItem_id()).get();
        Set<OrderItemResponseDTO> orderItemResponseDTOS = customerOrder.getOrderItem().stream().map(orderItem -> {
            OrderItemResponseDTO orderItemResponseDTO = new OrderItemResponseDTO();
            orderItemResponseDTO.setMenuId(orderItem.getMenu().getMenuItem_id());
            orderItemResponseDTO.setQuantity(orderItem.getQuantity());
            orderItemResponseDTO.setMenuName(menuItem.getItem_name());
            orderItemResponseDTO.setItemPrice(menuItem.getPrice());
            return orderItemResponseDTO;
        }).collect(Collectors.toSet());

        orderResponseDTO.setOrderItems(orderItemResponseDTOS);
        return orderResponseDTO;

    }
}

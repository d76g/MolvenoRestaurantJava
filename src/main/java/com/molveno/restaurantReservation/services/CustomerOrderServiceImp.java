package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.*;
import com.molveno.restaurantReservation.models.DTO.OrderDTO;

import com.molveno.restaurantReservation.models.DTO.Response.OrderItemResponseDTO;
import com.molveno.restaurantReservation.models.DTO.Response.OrderResponseDTO;
import com.molveno.restaurantReservation.models.DTO.Response.ReservationResponseDTO;
import com.molveno.restaurantReservation.models.DTO.Response.TableResponseDTO;
import com.molveno.restaurantReservation.repos.KitchenStockRepo;
import com.molveno.restaurantReservation.repos.MenuRepo;
import com.molveno.restaurantReservation.repos.OrderRepo;
import com.molveno.restaurantReservation.repos.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private KitchenStockRepo kitchenStockRepo;

    @Override
    public Iterable<CustomerOrder> findAll() {
        return orderRepo.findAll();
    }

    @Override
    public List<OrderResponseDTO> mapToResponseDTO(Iterable<CustomerOrder> customerOrders) {
        List<OrderResponseDTO> dtos = new ArrayList<>();
        for (CustomerOrder order : customerOrders) {
            dtos.add(mapToResponseDTO(order));
        }
        return dtos;
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

        CustomerOrder order;
        if (orderDTO.getId() != 0) {
            // Check if order exists and update it
            order = orderRepo.findById(orderDTO.getId()).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        } else {
            // Create a new order
            order = new CustomerOrder();
            order.setStatus("PENDING");
        }

        // Set reservation
        order.setReservation(reservationOpt.get());

        // Create new set of order items
        Set<OrderItem> newOrderItems = orderDTO.getOrderItems().stream().map(orderItemDTO -> {
            OrderItem newOrderItem = new OrderItem();
            newOrderItem.setOrder(order);
            newOrderItem.setMenu(menuRepo.findById(orderItemDTO.getMenuId())
                    .orElseThrow(() -> new IllegalArgumentException("Menu item not found")));
            newOrderItem.setQuantity(orderItemDTO.getQuantity());
            newOrderItem.setPrice(orderItemDTO.getPrice());
            return newOrderItem;
        }).collect(Collectors.toSet());

        // Clear existing order items and add new ones
        order.getOrderItem().clear();
        order.getOrderItem().addAll(newOrderItems);

        // Calculate total price
        double totalPrice = newOrderItems.stream().mapToDouble(orderItem ->
                orderItem.getMenu().getPrice() * orderItem.getQuantity()).sum();
        order.setTotal_price(totalPrice);

        // Save and return the order
        return orderRepo.save(order);
    }

    // place order and deduct from stock
    public CustomerOrder placeOrder(OrderDTO orderDto){
        CustomerOrder order = orderRepo.findById(orderDto.getId()).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus("PLACED");
        orderRepo.save(order);

        for (OrderItem orderItem : order.getOrderItem()) {
            Menu menuItem = orderItem.getMenu();

            // Deduct stock for each MenuItemStock entry
            for (MenuItemStock menuItemStock : menuItem.getMenuItemStocks()) {
                KitchenStock kitchenStock = menuItemStock.getKitchenStock();
                double amountToDeduct = menuItemStock.getAmount() * orderItem.getQuantity();
                kitchenStock.setStock(kitchenStock.getStock() - amountToDeduct);
                kitchenStockRepo.save(kitchenStock); // Assuming you have a KitchenStock repository
            }

             menuRepo.save(menuItem); // Assuming you have a Menu repository
        }
        return order;
    }

    // change order status
    @Override
    public CustomerOrder changeOrderStatus(long id, String status){
        CustomerOrder order = orderRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(status);
        return orderRepo.save(order);
    }

    @Override
    public void deleteById(long id) {
        if (!orderRepo.existsById(id)) {
            throw new IllegalArgumentException("Order not found");
        }
        orderRepo.deleteById(id);
    }

    // delete all orders
    @Override
    public void deleteAll() {
        orderRepo.deleteAll();
    }

    public OrderResponseDTO mapToResponseDTO(CustomerOrder customerOrder) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderId(customerOrder.getOrder_id());
        orderResponseDTO.setTotalPrice(customerOrder.getTotal_price());
        orderResponseDTO.setReservationId(customerOrder.getReservation().getId());
        orderResponseDTO.setStatus(customerOrder.getStatus());

        Set<OrderItemResponseDTO> orderItemResponseDTOS = customerOrder.getOrderItem().stream().map(orderItem -> {
            OrderItemResponseDTO orderItemResponseDTO = new OrderItemResponseDTO();
            Menu menuItem = menuRepo.findById(orderItem.getMenu().getMenuItem_id())
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));
            orderItemResponseDTO.setMenuId(orderItem.getMenu().getMenuItem_id());
            orderItemResponseDTO.setQuantity(orderItem.getQuantity());
            orderItemResponseDTO.setMenuName(menuItem.getItem_name());
            orderItemResponseDTO.setItemPrice(menuItem.getPrice());
            orderItemResponseDTO.setImage(menuItem.getImage());
            return orderItemResponseDTO;
        }).collect(Collectors.toSet());
        orderResponseDTO.setOrderItems(orderItemResponseDTOS);

        Reservation reservation = customerOrder.getReservation();
        if (reservation != null) {
            ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO();
            reservationResponseDTO.setId(reservation.getId());
            reservationResponseDTO.setCustomerFirstName(reservation.getCustomerFirstName());
            reservationResponseDTO.setCustomerLastName(reservation.getCustomerLastName());
            reservationResponseDTO.setCustomerEmail(reservation.getCustomerEmail());
            reservationResponseDTO.setCustomerPhone(reservation.getCustomerPhone());
            reservationResponseDTO.setReservationDate(reservation.getReservationDate().toString());
            reservationResponseDTO.setReservationTime(reservation.getReservationTime().toString());
            reservationResponseDTO.setNumberOfGuests(reservation.getNumberOfGuests());
            reservationResponseDTO.setGuest(reservation.isGuest());
            reservationResponseDTO.setRoomNumber(reservation.getRoomNumber());
            reservationResponseDTO.setReservationStatus(reservation.getReservationStatus());

            Set<TableResponseDTO> tables = reservation.getTables().stream()
                    .map(this::mapToTableResponseDTO)
                    .collect(Collectors.toSet());
            reservationResponseDTO.setTables(tables);

            orderResponseDTO.setReservation(reservationResponseDTO);
        }

        return orderResponseDTO;
    }
    private TableResponseDTO mapToTableResponseDTO(Table table) {
        TableResponseDTO tableResponseDTO = new TableResponseDTO();
        tableResponseDTO.setId(table.getId());
        tableResponseDTO.setTableNumber(table.getTableNumber());
        tableResponseDTO.setCapacity(table.getTableCapacity());
        return tableResponseDTO;
    }

}

package com.molveno.restaurantReservation.models.DTO.Mappers;

import com.molveno.restaurantReservation.models.DTO.Response.OrderItemResponseDTO;
import com.molveno.restaurantReservation.models.DTO.Response.OrderResponseDTO;
import com.molveno.restaurantReservation.models.DTO.Response.ReservationResponseDTO;
import com.molveno.restaurantReservation.models.DTO.Response.TableResponseDTO;
import com.molveno.restaurantReservation.models.Reservation;

import java.util.Set;
import java.util.stream.Collectors;

public class ReservationMapper {
    public static ReservationResponseDTO mapToResponseDTO(Reservation reservation) {
        ReservationResponseDTO dto = new ReservationResponseDTO();
        dto.setId(reservation.getId());
        dto.setCustomerFirstName(reservation.getCustomerFirstName());
        dto.setCustomerLastName(reservation.getCustomerLastName());
        dto.setCustomerEmail(reservation.getCustomerEmail());
        dto.setCustomerPhone(reservation.getCustomerPhone());
        dto.setReservationDate(reservation.getReservationDate());
        dto.setReservationTime(reservation.getReservationTime());
        dto.setNumberOfGuests(reservation.getNumberOfGuests());
        dto.setGuest(reservation.isGuest());
        dto.setRoomNumber(reservation.getRoomNumber());
        dto.setReservationStatus(reservation.getReservationStatus());

        Set<OrderResponseDTO> orders = reservation.getOrders().stream()
                .map(order -> {
                    OrderResponseDTO orderDTO = new OrderResponseDTO();
                    orderDTO.setOrderId(order.getOrder_id());
                    orderDTO.setTotalPrice(order.getTotal_price());

                    Set<OrderItemResponseDTO> orderItems = order.getOrderItem().stream()
                            .map(orderItem -> {
                                OrderItemResponseDTO orderItemDTO = new OrderItemResponseDTO();
                                orderItemDTO.setMenuId(orderItem.getMenu().getMenuItem_id());
                                orderItemDTO.setMenuName(orderItem.getMenu().getItem_name());
                                orderItemDTO.setQuantity(orderItem.getQuantity());
                                orderItemDTO.setItemPrice(orderItem.getPrice());
                                return orderItemDTO;
                            }).collect(Collectors.toSet());

                    orderDTO.setOrderItems(orderItems);
                    return orderDTO;
                }).collect(Collectors.toSet());

        dto.setOrders(orders);

        Set<TableResponseDTO> tables = reservation.getTables().stream()
                .map(table -> {
                    TableResponseDTO tableDTO = new TableResponseDTO();
                    tableDTO.setId(table.getId());
                    tableDTO.setTableNumber(table.getTableNumber());
                    tableDTO.setCapacity(table.getTableCapacity());
                    return tableDTO;
                }).collect(Collectors.toSet());

        dto.setTables(tables);

        return dto;
    }
}


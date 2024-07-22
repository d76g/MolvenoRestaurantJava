package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.CustomerOrder;
import com.molveno.restaurantReservation.models.DTO.OrderDTO;
import com.molveno.restaurantReservation.models.DTO.Response.OrderResponseDTO;
import com.molveno.restaurantReservation.services.CustomerOrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private CustomerOrderServiceImp customerOrderService;
    // get all orders
    @GetMapping(value = "/order", produces = "application/json")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        Iterable<CustomerOrder> allOrders = customerOrderService.findAll();
        List<OrderResponseDTO> responseDTOs = customerOrderService.mapToResponseDTO(allOrders);
        return ResponseEntity.ok(responseDTOs);
    }
    // get order by id
     @GetMapping(value = "/order/{id}", produces = "application/json")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable long id) {
         CustomerOrder order = customerOrderService.findById(id);
        return ResponseEntity.ok(customerOrderService.mapToResponseDTO(order));
    }

    // get order by reservation id
    @GetMapping(value = "/order/reservation/{id}", produces = "application/json")
    public ResponseEntity<List<OrderResponseDTO>> getOrderByReservationId(@PathVariable long id) {
        Iterable<CustomerOrder> orders = customerOrderService.findByReservationId(id);
        List<OrderResponseDTO> responseDTOs = customerOrderService.mapToResponseDTO(orders);
        return ResponseEntity.ok(responseDTOs);
    }

    // save order
    @PostMapping(value = "/order", produces = "application/json", consumes = "application/json")
    public ResponseEntity<OrderResponseDTO> saveOrder(@RequestBody OrderDTO order) {
         CustomerOrder newOrder = customerOrderService.save(order);
        return ResponseEntity.ok(customerOrderService.mapToResponseDTO(newOrder));
    }

    // place order
    @PostMapping(value = "/order/place", produces = "application/json")
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody OrderDTO order) {
        CustomerOrder placedOrder = customerOrderService.placeOrder(order);
        return ResponseEntity.ok(customerOrderService.mapToResponseDTO(placedOrder));
    }
    // update order status
    @PostMapping(value = "/order/{id}/status/{orderStatus}", produces = "application/json")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(@PathVariable long id, @PathVariable String orderStatus) {
        CustomerOrder updatedOrder = customerOrderService.changeOrderStatus(id, orderStatus);
        return ResponseEntity.ok(customerOrderService.mapToResponseDTO(updatedOrder));
    }

    // delete order by id
    @DeleteMapping(value = "/order/{id}", produces = "application/json")
    public void deleteOrderById(@PathVariable long id) {
        customerOrderService.deleteById(id);
    }

    // delete all orders
    @DeleteMapping(value = "/order", produces = "application/json")
    public ResponseEntity<String> deleteAllOrders() {
        customerOrderService.deleteAll();
        return ResponseEntity.ok("All orders deleted");
    }


}

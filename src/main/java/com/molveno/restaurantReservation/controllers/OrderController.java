package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.CustomerOrder;
import com.molveno.restaurantReservation.models.DTO.OrderDTO;
import com.molveno.restaurantReservation.models.DTO.Response.OrderResponseDTO;
import com.molveno.restaurantReservation.services.CustomerOrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private CustomerOrderServiceImp customerOrderService;
    // get all orders
    @GetMapping(value = "/order", produces = "application/json")
    public ResponseEntity<Iterable<CustomerOrder>> getAllOrders() {
        Iterable<CustomerOrder> allOrders = customerOrderService.findAll();
        return ResponseEntity.ok(allOrders);
    }
    // get order by id
     @GetMapping(value = "/order/{id}", produces = "application/json")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable long id) {
         CustomerOrder order = customerOrderService.findById(id);
        return ResponseEntity.ok(customerOrderService.mapToResponseDTO(order));
    }

    // save order
    @PostMapping(value = "/order", produces = "application/json", consumes = "application/json")
    public ResponseEntity<OrderResponseDTO> saveOrder(@RequestBody OrderDTO order) {
         CustomerOrder newOrder = customerOrderService.save(order);
        return ResponseEntity.ok(customerOrderService.mapToResponseDTO(newOrder));
    }

    // delete all orders
    @DeleteMapping(value = "/order", produces = "application/json")
    public ResponseEntity<String> deleteAllOrders() {
        customerOrderService.deleteAll();
        return ResponseEntity.ok("All orders deleted");
    }
}

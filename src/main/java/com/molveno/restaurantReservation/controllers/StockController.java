package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.KitchenStock;
import com.molveno.restaurantReservation.models.Table;
import com.molveno.restaurantReservation.services.KitchenStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StockController {
    @Autowired
    KitchenStockService kitchenStockService;

    @GetMapping( value = "/stock", produces = "application/json")
    public ResponseEntity<Iterable<KitchenStock>> getKitchenStock() {
        Iterable<KitchenStock> kitchenStocks = kitchenStockService.getKitchenStocks();
        return ResponseEntity.ok(kitchenStocks);
    }


  @PostMapping( value = "/stock" , produces = "application/json", consumes = "application/json")

    public ResponseEntity<KitchenStock> add(@RequestBody KitchenStock kitchenStock) {
      KitchenStock newkitchenStock = kitchenStockService.addKitchenStock(kitchenStock);
      return new ResponseEntity<>(newkitchenStock, HttpStatus.CREATED);
   }

    @DeleteMapping(value = "/stock/{id}", produces = "application/json")
    public ResponseEntity<KitchenStock> delete(@PathVariable Long id) {
        kitchenStockService.deleteKitchenStock(id);
       return  ResponseEntity.noContent().build();
    }


}

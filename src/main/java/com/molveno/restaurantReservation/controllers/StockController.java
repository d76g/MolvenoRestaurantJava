package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.DTO.StockConverter;
import com.molveno.restaurantReservation.models.DTO.StockDTO;
import com.molveno.restaurantReservation.models.KitchenCategory;
import com.molveno.restaurantReservation.models.KitchenStock;
import com.molveno.restaurantReservation.models.Table;
import com.molveno.restaurantReservation.services.KitchenCategoryService;
import com.molveno.restaurantReservation.services.KitchenStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api")
public class StockController {
    @Autowired
    KitchenStockService kitchenStockService;

    @Autowired
    KitchenCategoryService kitchenCategoryService;

    @GetMapping(value = "/stock", produces = "application/json")
    public ResponseEntity<Iterable<StockDTO>> getKitchenStock() {
        Iterable<KitchenStock> kitchenStocks = kitchenStockService.getKitchenStocks();
        Iterable<StockDTO> kitchenStockDTOs = StreamSupport.stream(kitchenStocks.spliterator(), false)
                .map(StockConverter::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(kitchenStockDTOs);
    }


    @PostMapping(value = "/stock", produces = "application/json", consumes = "application/json")
    public ResponseEntity<StockDTO> add(@RequestBody StockDTO stockDTO) {
        System.out.println(stockDTO);
        KitchenCategory kitchenCategory = kitchenCategoryService.getKitchenCategoryById(stockDTO.getCategory().getId());
        KitchenStock kitchenStock = StockConverter.toEntity(stockDTO, kitchenCategory);
        Optional<KitchenCategory> optionalKitchenCategory = Optional.ofNullable(kitchenCategoryService.getKitchenCategoryById(stockDTO.getCategory().getId()));
        if (optionalKitchenCategory.isPresent()) {
            kitchenStock.setCategory(optionalKitchenCategory.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        kitchenStockService.addKitchenStock(kitchenStock);
        return ResponseEntity.ok(stockDTO);
    }

    @DeleteMapping(value = "/stock/{id}", produces = "application/json")
    public ResponseEntity<KitchenStock> delete(@PathVariable Long id) {
        kitchenStockService.deleteKitchenStock(id);
       return  ResponseEntity.noContent().build();
    }


}

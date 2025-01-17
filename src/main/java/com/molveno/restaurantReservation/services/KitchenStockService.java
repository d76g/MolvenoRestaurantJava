package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.KitchenStock;

import java.util.List;

public interface KitchenStockService {
    //read
    Iterable<KitchenStock> getKitchenStocks();

    //create
    KitchenStock addKitchenStock(KitchenStock kitchenStock);

    //FIND
    KitchenStock addKitchenStockById(Long stock_id);

    //DELETE
    void deleteKitchenStock(long stock_id);

    //UPDATE
    KitchenStock updateKitchenStock(KitchenStock kitchenStock);

    //check if a stock is below the stock limit
    Iterable<KitchenStock> checkStockLimit();
}

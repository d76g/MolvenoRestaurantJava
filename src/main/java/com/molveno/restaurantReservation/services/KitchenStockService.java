package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.KitchenStock;
import com.molveno.restaurantReservation.repos.KitchenStockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class KitchenStockService {
    final KitchenStockRepo kitchenStockRepo;

    @Autowired
    public KitchenStockService(KitchenStockRepo kitchenStockRepo) {
        this.kitchenStockRepo = kitchenStockRepo;
    }
    //read
    public List<KitchenStock> getKitchenStocks() {
        return kitchenStockRepo.findAll();
    }
    //create
    public KitchenStock addKitchenStock(KitchenStock kitchenStock) {
      return kitchenStockRepo.save(kitchenStock);
    }

    //FIND
    public KitchenStock getKitchenStock(Long stock_id) {
        return kitchenStockRepo.findById(stock_id).get();
    }
//DELETE
    public void deleteKitchenStock(Long stock_id) {
        kitchenStockRepo.deleteById(stock_id);
    }
    //UPDATE
    public KitchenStock updateKitchenStock(KitchenStock kitchenStock) {
        return kitchenStockRepo.save(kitchenStock);
    }

}

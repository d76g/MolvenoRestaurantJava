package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.KitchenStock;
import com.molveno.restaurantReservation.repos.KitchenStockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class KitchenStockServiceImp implements KitchenStockService {
    final KitchenStockRepo kitchenStockRepo;

    @Autowired
    public KitchenStockServiceImp(KitchenStockRepo kitchenStockRepo) {
        this.kitchenStockRepo = kitchenStockRepo;
    }
    //read
    @Override
    public Iterable<KitchenStock> getKitchenStocks() {
        return kitchenStockRepo.findAll();
    }
    //create
    @Override
    public KitchenStock addKitchenStock(KitchenStock kitchenStock) {
      return kitchenStockRepo.save(kitchenStock);
    }

    //FIND
    @Override
    public KitchenStock addKitchenStockById(Long stock_id) {
        return kitchenStockRepo.findById(stock_id).get();
    }
//DELETE
   @Override
    public void deleteKitchenStock(Long stock_id) {
        kitchenStockRepo.deleteById(stock_id);
    }
    //UPDATE
    @Override
    public KitchenStock updateKitchenStock(KitchenStock kitchenStock) {
        return kitchenStockRepo.save(kitchenStock);
    }

}

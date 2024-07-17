package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.KitchenCategory;
import com.molveno.restaurantReservation.models.KitchenStock;
import com.molveno.restaurantReservation.repos.KitchenCategoryRepo;
import com.molveno.restaurantReservation.repos.KitchenStockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class KitchenStockServiceImp implements KitchenStockService {

    final KitchenStockRepo kitchenStockRepo;
    private final KitchenCategoryRepo kitchenCategoryRepo;

    @Autowired
    public KitchenStockServiceImp(KitchenStockRepo kitchenStockRepo, KitchenCategoryRepo kitchenCategoryRepo) {
        this.kitchenStockRepo = kitchenStockRepo;
        this.kitchenCategoryRepo = kitchenCategoryRepo;
    }
    //read
    @Override
    public Iterable<KitchenStock> getKitchenStocks() {
        return kitchenStockRepo.findAll();
    }
    //create
    @Override
    public KitchenStock addKitchenStock(KitchenStock kitchenStock) {
    // Check if the category_id is provided and valid
        if (kitchenStock.getCategory() != null && kitchenStock.getCategory().getCategory_id() != null) {
            // Retrieve the KitchenCategory from the repository based on category_id
            Optional<KitchenCategory> optionalKitchenCategory = kitchenCategoryRepo.findById(kitchenStock.getCategory().getCategory_id());

            // Check if the KitchenCategory exists
            if (optionalKitchenCategory.isPresent()) {
                // Associate the KitchenStock with the retrieved KitchenCategory
                kitchenStock.setCategory(optionalKitchenCategory.get());
            } else {
                // Return null or handle the case where the category_id does not correspond to an existing KitchenCategory
                throw  new RuntimeException("Category not found");
            }
        } else {
            // Return null or handle the case where category or category_id is not provided
            throw new RuntimeException("Category ID not found");
        }

        // Save the KitchenStock with the associated KitchenCategory
        return kitchenStockRepo.save(kitchenStock);
    }

    //FIND
    @Override
    public KitchenStock addKitchenStockById(Long stock_id) {
        return kitchenStockRepo.findById(stock_id).get();
    }
//DELETE
   @Override
    public void deleteKitchenStock(long stock_id) {
       System.out.println("stock_id = " + stock_id);
        kitchenStockRepo.deleteById(stock_id);
    }
    //UPDATE
    @Override
    public KitchenStock updateKitchenStock(KitchenStock kitchenStock) {
        return kitchenStockRepo.save(kitchenStock);
    }

    @Override
    public Iterable<KitchenStock> checkStockLimit() {
        // loop through the all the kitchen stock items and check if stock is below stock value
        KitchenStock kitchenStock = new KitchenStock();
        return kitchenStockRepo.findByStockLessThan();
    }



}

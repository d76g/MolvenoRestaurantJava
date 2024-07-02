package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.KitchenCategory;
import com.molveno.restaurantReservation.repos.KitchenCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KitchenCategoryServiceImp implements KitchenCategoryService {
    final KitchenCategoryRepo kitchenCategoryRepo;
    @Autowired
    public KitchenCategoryServiceImp(KitchenCategoryRepo kitchenCategoryRepo) {
        this.kitchenCategoryRepo = kitchenCategoryRepo;
    }
//read
    @Override
    public Iterable<KitchenCategory> getKitchenCategories() {
        return kitchenCategoryRepo.findAll();
    }
//create
     @Override
    public KitchenCategory addKitchenCategory(KitchenCategory kitchenCategory) {
        return kitchenCategoryRepo.save(kitchenCategory);
    }
    //FIND
    @Override
    public KitchenCategory getKitchenCategoryById(Long category_id) {
        return kitchenCategoryRepo.findById(category_id).get();
    }
   @Override
    //DELETE
    public void deleteKitchenCategory(Long category_id) {
        kitchenCategoryRepo.deleteById(category_id);
    }
    //UPDATE
    @Override
    public KitchenCategory updateKitchenCategory(KitchenCategory kitchenCategory) {
        return kitchenCategoryRepo.save(kitchenCategory);
    }

}

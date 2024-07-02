package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.SubCategory;
import com.molveno.restaurantReservation.repos.SubCategoryRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    private SubCategoryRepo subCategoryRepo;

    @Override
    public SubCategory save(SubCategory subCategory) {
        if( subCategory.getSubCategory_id() == 0){
            System.out.println("Creating subCategory");
        }else{
            System.out.println("updating subCategory");
        }
        return subCategoryRepo.save(subCategory);
    }

    @Override
    public Iterable<SubCategory> listSubCategory() {
        return subCategoryRepo.findAll();
    }

    @Override
    public Optional<SubCategory> getSubCategoryById(long id) {
        return subCategoryRepo.findById(id);
    }

    @Override
    public void deleteSubCategory(long id) {
        subCategoryRepo.deleteById(id);

    }

}

package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.SubCategory;
import java.util.Optional;

public interface SubCategoryService {
    SubCategory save(SubCategory subCategory);
    Iterable<SubCategory> listSubCategory();
    Optional<SubCategory> getSubCategoryById(long id);
    void deleteSubCategory(long id);

}

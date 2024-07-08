package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.SubCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepo extends CrudRepository<SubCategory, Long> {
}

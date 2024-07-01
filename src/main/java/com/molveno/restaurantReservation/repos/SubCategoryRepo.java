package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepo extends JpaRepository<SubCategory, Long> {
}

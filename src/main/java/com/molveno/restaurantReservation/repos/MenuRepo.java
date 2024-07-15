package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepo extends CrudRepository<Menu, Long > {
}

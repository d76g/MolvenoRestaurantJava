package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends CrudRepository<UserRole, Long> {
    // interact with the database
    UserRole findByRole(String role);
}

package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Long> {
    // interact with the database
    UserRole findByRole(String role);
}

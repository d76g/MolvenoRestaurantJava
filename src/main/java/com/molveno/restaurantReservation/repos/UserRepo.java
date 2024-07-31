package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
     User findByUsername(String username);
     User findByEmail(String email);
   }

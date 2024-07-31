package com.molveno.restaurantReservation.repos;

import com.molveno.restaurantReservation.models.ForgetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgetPasswordToken,Long> {

    ForgetPasswordToken findByToken(String token);


}

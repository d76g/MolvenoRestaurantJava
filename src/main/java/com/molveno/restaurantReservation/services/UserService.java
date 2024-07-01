package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.Users;
import com.molveno.restaurantReservation.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepo userRepo;
    @Autowired

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }
}

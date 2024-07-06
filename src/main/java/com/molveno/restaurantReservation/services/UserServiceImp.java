package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.User;
import com.molveno.restaurantReservation.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired

    private UserRepo userRepo;

    // save
    @Override
    public User createUser(User user) {
        // validateUser(user);
        return userRepo.save(user);
    }

    @Override
    public List<User> listUser() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(long id) {
        return null;
    }

    @Override
    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }

}

package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    List<User> listUser();

    User getUserById(long id);

    void deleteUser(long id);

}

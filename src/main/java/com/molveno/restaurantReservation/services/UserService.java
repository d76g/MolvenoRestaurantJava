package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.DTO.UserDTO;
import com.molveno.restaurantReservation.models.User;

import java.util.List;

public interface UserService {
    // add new user
    UserDTO saveUser(UserDTO user);

    // get user by id
    List<UserDTO> listUser();

    User getUserById(long id);

    // delete user
    void deleteUser(long id);

}

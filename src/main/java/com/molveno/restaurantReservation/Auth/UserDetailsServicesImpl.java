package com.molveno.restaurantReservation.Auth;

import com.molveno.restaurantReservation.models.User;
import com.molveno.restaurantReservation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDetailsServicesImpl {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username)
        throws  UsernameNotFoundException {
        User user = userService.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new UserDetails(username);
    }
}

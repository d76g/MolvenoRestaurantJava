package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.UserRole;
import com.molveno.restaurantReservation.repos.UserRoleRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleRepo userRoleRepo;

    @PostConstruct
    @Override
    public void initRoles() {
        List<String> roles = Arrays.asList("Admin", "Chef", "Front desk", "Waiter");
        System.out.println("Creating roles ");
        for (String roleName : roles) {
            if (userRoleRepo.findByRole(roleName) == null) {
                UserRole role = new UserRole();
                role.setRole(roleName);
                userRoleRepo.save(role);
            }
        }
    }
}

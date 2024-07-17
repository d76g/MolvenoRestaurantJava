package com.molveno.restaurantReservation.controllers;

import ch.qos.logback.core.model.Model;
import com.molveno.restaurantReservation.models.DTO.UserDTO;
import com.molveno.restaurantReservation.models.User;
import com.molveno.restaurantReservation.repos.UserRepo;
import com.molveno.restaurantReservation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

// List of  all Users
    @GetMapping("/users/all")
    public ResponseEntity<Iterable<UserDTO>> listUsers(){
    List<UserDTO> users= userService.listUser();
    return ResponseEntity.ok(users);
    }

    @PostMapping(value = "/users", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addUser(@RequestBody UserDTO user) {
        System.out.println("Inside addUser");

        // Validate password complexity
        if (!isPasswordComplex(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Password does not meet complexity requirements. It must be at least 8 characters long," +
                          " contain at least one uppercase letter, one lowercase letter, one digit," +
                          " and one special character.");
        }

        UserDTO newUser = userService.saveUser(user);
        return ResponseEntity.ok(newUser);
    }

    // Delete user
    @DeleteMapping(value = "/users/{id}")
    public void deleteUser(@PathVariable long id) {
        System.out.println("Inside deleteUser");
        userService.deleteUser(id);
    }
    private boolean isPasswordComplex(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }

        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    }

}



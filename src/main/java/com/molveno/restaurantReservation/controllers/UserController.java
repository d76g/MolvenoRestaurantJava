package com.molveno.restaurantReservation.controllers;

import com.molveno.restaurantReservation.models.User;
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
    public ResponseEntity<Iterable<User>> listUsers(){
    List<User> users= userService.listUser();
    return ResponseEntity.ok(users);
    }
    //add new user
    @PostMapping(value ="/users",consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        System.out.println("Inside addUser");
        User newUser = userService.createUser(user);
        return  ResponseEntity.ok(newUser);
        }

    // Delete user
    @DeleteMapping(value = "/users/{id}")
    public void deleteUser(@PathVariable long id) {
        System.out.println("Inside deleteUser");
        userService.deleteUser(id);
    }
}



package com.molveno.restaurantReservation.controllers;

import ch.qos.logback.core.model.Model;
import com.molveno.restaurantReservation.models.Users;
import com.molveno.restaurantReservation.services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

@Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


@GetMapping("/user")
    public ResponseEntity<Iterable<Users>> getAllUsers(){
    List<Users> users=userService.getAllUsers();
    return ResponseEntity.ok(users);
    }

}

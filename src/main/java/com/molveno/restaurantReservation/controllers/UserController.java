package com.molveno.restaurantReservation.controllers;

import ch.qos.logback.core.model.Model;
import com.molveno.restaurantReservation.models.Users;
import com.molveno.restaurantReservation.services.UserService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.hibernate.mapping.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

@Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


@GetMapping("/users/all")
    public ResponseEntity<Iterable<Users>> getAllUsers(){
    List<Users> users=userService.getAllUsers();
    return ResponseEntity.ok(users);
    }
    @PostMapping("/users/add")
    public ResponseEntity<Users> addTable(@ModelAttribute("user") @Valid UserService user) {
        Table serviceUser = userService.createUser (userService);
        return new ResponseEntity<>(new Users(), HttpStatus.CREATED);
    }
   // @PostMapping("/table/add")
    //public ResponseEntity<Table> addTable(@ModelAttribute("table") @Valid Table table) {
      //  Table newTable = tableService.createTable(table);
        //return new ResponseEntity<>(newTable, HttpStatus.CREATED);
    //}
//}
@PostMapping("/updateUser/{id}")
public ResponseEntity<Users> updateTable(@PathVariable long id, @ModelAttribute Users table) {
    Users updatedUser = userService.updateTableCapacity(id, table.getTableCapacity());
    if (updatedTable  != null) {
        return new ResponseEntity<>(updatedTable, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
//@PostMapping("/updateTable/{id}")
//public ResponseEntity<Table> updateTable(@PathVariable long id, @ModelAttribute Table table) {
  //  Table updatedTable = tableService.updateTableCapacity(id, table.getTableCapacity());
    //if (updatedTable != null) {
      //  return new ResponseEntity<>(updatedTable, HttpStatus.OK);
    //} else {
      //  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //}
//}

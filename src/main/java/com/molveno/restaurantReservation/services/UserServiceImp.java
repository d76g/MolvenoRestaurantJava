package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.DTO.UserDTO;
import com.molveno.restaurantReservation.models.User;
import com.molveno.restaurantReservation.models.UserRole;
import com.molveno.restaurantReservation.repos.UserRepo;
import com.molveno.restaurantReservation.repos.UserRoleRepo;
import com.molveno.restaurantReservation.utils.UserValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    UserRoleRepo userRoleRepo;

    // save
    @Override
    public UserDTO saveUser(UserDTO userDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user;
        if (userDto.getUserId() != 0) {
            user = userRepo.findById(userDto.getUserId()).orElse(new User());
        } else {
            user = new User();
        }

        // Check if user with the same username already exists
        User existingUser = userRepo.findByUsername(userDto.getUserName());
        if (existingUser != null && (userDto.getUserId() == 0 || existingUser.getUser_id() != userDto.getUserId())) {
            // User already exists, throw error
            throw new UserValidationException("Username already exists", "username");
        }

        user.setUsername(userDto.getUserName());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        // If password is not empty, then encode it
        if (!userDto.getPassword().isEmpty()) {
            // Validate password complexity
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        } else {
            user.setPassword(userRepo.findById(userDto.getUserId()).orElse(user).getPassword());
        }

        UserRole userRole = userRoleRepo.findByRole(userDto.getRoleName());
        user.setUserRole(userRole);

        userRepo.save(user);
        return convertToDTO(user);
    }

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }


    @Override
    public List<UserDTO> listUser() {
        List<User> users = userRepo.findAll();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUser_id());
        userDTO.setUserName(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoleName(user.getUserRole().getRole());
        userDTO.setPassword(user.getPassword());
        userDTO.setRoleId(user.getUserRole().getRole_id());
        return userDTO;
    }

    @Override
    public User getUserById(long id) {
        return null;
    }

    @Override
    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }


}

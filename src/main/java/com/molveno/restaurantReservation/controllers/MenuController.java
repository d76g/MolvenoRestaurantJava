package com.molveno.restaurantReservation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.molveno.restaurantReservation.models.DTO.MenuDTO;
import com.molveno.restaurantReservation.models.MealTime;
import com.molveno.restaurantReservation.services.AzureBlobUploadService;
import com.molveno.restaurantReservation.services.MenuService;
import com.molveno.restaurantReservation.services.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class MenuController {
    @Autowired
    private MenuServiceImpl menuService;

    // get all menu
    @GetMapping("/menu/all")
    public ResponseEntity<Iterable<MenuDTO>> getAllMenu() {
        System.out.println("Inside getAllMenu");
        Iterable<MenuDTO> allMenu = menuService.listMenu();
        return ResponseEntity.ok(allMenu);
    }

    @Autowired
    private AzureBlobUploadService azureBlobUploadService;

    @PostMapping(value = "/menu/add", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<MenuDTO> addMenuItem(
            @RequestPart("menuDto") String menuDtoJson, // JSON as a string
            @RequestPart("image") MultipartFile image) throws IOException {

        System.out.println("Inside addMenuItem");

        // Convert the JSON string to MenuDTO
        ObjectMapper objectMapper = new ObjectMapper();
        MenuDTO menuDto = objectMapper.readValue(menuDtoJson, MenuDTO.class);

        // Upload the image to Azure Blob Storage and get the URL
        String imageUrl = azureBlobUploadService.uploadImageToAzure(image, menuDto.getItem_name());

        // Set the image URL in the MenuDTO
        menuDto.setImageUrl(imageUrl);
        // Save the menu item with the image URL
        MenuDTO savedMenu = menuService.saveMenuItem(menuDto);

        return ResponseEntity.ok(savedMenu);
    }




    // delete menuItem
    @DeleteMapping(value = "menu/{id}")
    public void deleteMenuItem(@PathVariable long id) {
        System.out.println("Inside deleteMenuItem");
        menuService.deleteMenuItem(id);
    }


    // get menu item base on meal time
    @GetMapping(value = "/menu/{mealTime}")
    public ResponseEntity<Iterable<MenuDTO>> getMenuByMealTime(@PathVariable String mealTime) {
        System.out.println("Inside getMenuByMealTime");
        Iterable<MenuDTO> menuByMealTime = menuService.getMenuByMealTime(mealTime);
        return ResponseEntity.ok(menuByMealTime);
    }
}



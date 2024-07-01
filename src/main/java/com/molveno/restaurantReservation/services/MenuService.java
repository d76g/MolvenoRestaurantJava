package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.Menu;
import com.molveno.restaurantReservation.repos.MenuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    // define menu repository
    private MenuRepo menuRepo;

    @Autowired

    public MenuService(MenuRepo menuRepo) {
        this.menuRepo = menuRepo;
    }

    // add new menuItem
    public Menu createMenuItem(Menu menuItem) //throws TableValidationException
    {
        //validateMenu(menuItem);
        return menuRepo.save(menuItem);
    }

    // get all menuItems
    public List<Menu> getAllMenu() {
        return menuRepo.findAll();
    }

    // get menuItem by id
    public Menu getMenuItem(long id) {
        return menuRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Menu Item not found"));
    }

    public void updateMenuItem(long id){

    }

    public void deleteMenuItem(long id) {
        menuRepo.deleteById(id);
    }





}

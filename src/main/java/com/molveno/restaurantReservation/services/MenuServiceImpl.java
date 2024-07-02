package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.Menu;
import com.molveno.restaurantReservation.repos.MenuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService{

    // define repository
    private MenuRepo menuRepo;


    @Override
    public Menu saveMenuItem(Menu menu) {
        if( menu.getMenuItem_id() == 0){
            System.out.println("Creating user");
        }else{
            System.out.println("updating user");
        }
        return menuRepo.save(menu);
    }

    @Override
    public Iterable<Menu> listMenu() {
        return menuRepo.findAll();
    }

    @Override
    public Optional<Menu> getMenuItemById(long id) {
        return menuRepo.findById(id);
    }

    @Override
    public void deleteMenuItem(long id) {
        menuRepo.deleteById(id);

    }




}

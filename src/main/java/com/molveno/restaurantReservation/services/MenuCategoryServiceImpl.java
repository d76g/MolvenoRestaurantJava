package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.MenuCategory;
import com.molveno.restaurantReservation.repos.MenuCategoryRepo;
import com.molveno.restaurantReservation.repos.MenuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MenuCategoryServiceImpl implements MenuCategoryService {
    @Autowired
    private MenuCategoryRepo menuCategoryRepo;


    @Override
    public MenuCategory save(MenuCategory menuCategory) {
        if( menuCategory.getMenuCategory_id() == 0){
            System.out.println("Creating user");
        }else{
            System.out.println("updating user");
        }
        return menuCategoryRepo.save(menuCategory);
    }

    @Override
    public Iterable<MenuCategory> listMenuCategory() {
        return menuCategoryRepo.findAll();
    }

    @Override
    public Optional<MenuCategory> getMenuCategoryById(long id) {
        return menuCategoryRepo.findById(id);
    }

    @Override
    public void deleteMenuCategory(long id) {
        menuCategoryRepo.deleteById(id);
    }
}

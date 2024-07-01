package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.Menu;
import com.molveno.restaurantReservation.models.MenuCategory;
import com.molveno.restaurantReservation.repos.MenuCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuCategoryService {
    private MenuCategoryRepo menuCategoryRepo;

    @Autowired
    public MenuCategoryService(MenuCategoryRepo menuCategoryRepo) {
        this.menuCategoryRepo = menuCategoryRepo;
    }

    public MenuCategory createMenuCategory(MenuCategory menuCategory) //throws TableValidationException
    {
        //validateMenu(menuItem);
        return menuCategoryRepo.save(menuCategory);
    }

    public List<MenuCategory> listMenuCategory() {
        return menuCategoryRepo.findAll();
    }

//    public Menu getMenuCategory(long id) {
//        return menuCategoryRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Menu Item not found"));
//    }

    public void updateMenuCategory(long id){

    }

    public void deleteMenuCategory(long id) {
        menuCategoryRepo.deleteById(id);
    }

}

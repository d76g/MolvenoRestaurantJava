package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.Menu;
import com.molveno.restaurantReservation.models.MenuCategory;
import com.molveno.restaurantReservation.models.MenuDTO;
import com.molveno.restaurantReservation.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MenuServiceImpl implements MenuService{

    // define repository
    @Autowired
    private MenuRepo menuRepo;

    @Autowired
    private MenuCategoryRepo menuCategoryRepo;

    @Autowired
    private SubCategoryRepo subCategoryRepo;

    @Autowired
    private MealTimeRepo mealTimeRepo;
    @Autowired
    private KitchenStockRepo kitchenStockRepo;


    @Override
    public MenuDTO saveMenuItem(MenuDTO menuDto) {
        Optional<Menu> optionalMenu = menuRepo.findById(menuDto.getMenuItem_id());
        Menu menu;

        if (optionalMenu.isPresent()) {
            menu = optionalMenu.get();
            // Update the existing menu item fields with values from the DTO
            menu.setItem_name(menuDto.getItem_name());
            menu.setDescription(menuDto.getDescription());
            menu.setPrice(menuDto.getPrice());
            menu.setImage(menuDto.getImage());
            if (menuDto.getMenuCategoryId() > 0) {
                menu.setMenuCategory(menuCategoryRepo.findById(menuDto.getMenuCategoryId()).orElse(null));
            }
            if (menuDto.getSubCategoryId() > 0) {
                menu.setSubCategory(subCategoryRepo.findById(menuDto.getSubCategoryId()).orElse(null));
            }
            if (menuDto.getMealTimeId() > 0) {
                menu.setMealTime(mealTimeRepo.findById(menuDto.getMealTimeId()).orElse(null));
            }
        } else {
            // If the item does not exist, create a new one
            menu = dtoToEntity(menuDto);
        }

        Menu savedMenu = menuRepo.save(menu);
        return entityToDto(savedMenu);
    }


    @Override
    public Iterable<MenuDTO> listMenu() {
        Iterable<Menu> menus = menuRepo.findAll();
        return StreamSupport.stream(menus.spliterator(), false)
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Menu> getMenuItemById(long id) {
        return menuRepo.findById(id);
    }

    @Override
    public void deleteMenuItem(long id) {
        menuRepo.deleteById(id);

    }

    private Menu dtoToEntity(MenuDTO menuDto) {
        Menu menu = new Menu();
        menu.setMenuItem_id(menuDto.getMenuItem_id());
        menu.setItem_name(menuDto.getItem_name());
        menu.setDescription(menuDto.getDescription());
        menu.setPrice(menuDto.getPrice());
        menu.setImage(menuDto.getImage());

        if (menuDto.getMenuCategoryId() > 0) {
            menu.setMenuCategory(menuCategoryRepo.findById(menuDto.getMenuCategoryId()).orElse(null));
        }
        if (menuDto.getSubCategoryId() > 0) {
            menu.setSubCategory(subCategoryRepo.findById(menuDto.getSubCategoryId()).orElse(null));
        }
        if (menuDto.getMealTimeId() > 0) {
            menu.setMealTime(mealTimeRepo.findById(menuDto.getMealTimeId()).orElse(null));
        }

        return menu;
    }

    private MenuDTO entityToDto(Menu menu) {
        MenuDTO menuDto = new MenuDTO();
        menuDto.setMenuItem_id(menu.getMenuItem_id());
        menuDto.setItem_name(menu.getItem_name());
        menuDto.setDescription(menu.getDescription());
        menuDto.setPrice(menu.getPrice());
        menuDto.setImage(menu.getImage());

        if (menu.getMenuCategory() != null) {
            menuDto.setMenuCategoryId(menu.getMenuCategory().getMenuCategory_id());
            menuDto.setMenuCategoryName(menu.getMenuCategory().getMenuCategory_name());
        }
        if (menu.getSubCategory() != null) {
            menuDto.setSubCategoryId(menu.getSubCategory().getSubCategory_id());
            menuDto.setSubCategoryName(menu.getSubCategory().getSubCategory_name());
        }
        if (menu.getMealTime() != null) {
            menuDto.setMealTimeId(menu.getMealTime().getMealTime_id());
            menuDto.setMealTimeName(menu.getMealTime().getMealTime_name());
        }

        return menuDto;
    }




}

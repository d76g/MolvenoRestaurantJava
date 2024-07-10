package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.MenuItemStock;
import com.molveno.restaurantReservation.repos.MenuItemStockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuItemStockServiceImpl implements MenuItemStockService {
    final MenuItemStockRepo menuItemStockRepo;
@Autowired
    public MenuItemStockServiceImpl(MenuItemStockRepo menuItemStockRepo) {
        this.menuItemStockRepo = menuItemStockRepo;
    }
    //create
    @Override
    public void addMenuItemStock(MenuItemStock menuItemStock) {
        menuItemStockRepo.save(menuItemStock);
    }
    //read
    @Override
    public Iterable<MenuItemStock> getMenuItemStocks() {
        return menuItemStockRepo.findAll();
    }
    //find
    @Override
    public MenuItemStock getMenuItemStockById(Long menuItemStock_id) {
        return menuItemStockRepo.findById(menuItemStock_id).get();
    }

    //delete
    @Override
    public void deleteMenuItemStock(Long menuItemStock_id) {
        menuItemStockRepo.deleteById(menuItemStock_id);
    }

    //update
    @Override
    public MenuItemStock updateMenuItemStock(MenuItemStock menuItemStock) {
        return menuItemStockRepo.save(menuItemStock);
    }

    @Override
    public MenuItemStock findById(Long id) {
        return null;
    }
}

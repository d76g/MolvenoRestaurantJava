package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.KitchenStock;
import com.molveno.restaurantReservation.models.Menu;
import com.molveno.restaurantReservation.models.MenuDTO;
import com.molveno.restaurantReservation.models.MenuItemStock;
import com.molveno.restaurantReservation.repos.KitchenStockRepo;
import com.molveno.restaurantReservation.repos.MenuItemStockRepo;
import com.molveno.restaurantReservation.repos.MenuRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MenuItemStockServiceImpl implements MenuItemStockService {
    @Autowired
    private MenuRepo menuRepo;

    @Autowired
    private KitchenStockRepo kitchenStockRepo;

    @Autowired
    private MenuItemStockRepo menuItemStockRepo;

    //create
    @Override
    public MenuItemStock addMenuItemStock(MenuItemStock menuItemStock) {
        menuItemStockRepo.save(menuItemStock);
        return menuItemStock;
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

    @Transactional
    public void placeOrder(String menuItemName) throws Exception {
        Menu menu = menuRepo.findByName(menuItemName);
        if (menu == null) {
            throw new Exception("Menu item not found");
        }

        Set<MenuItemStock> items = menu.getMenuItemStocks(); // ?

        for (MenuItemStock item : items) {
            KitchenStock stock = kitchenStockRepo.findById(item.getKitchenStock().getId())
                    .orElseThrow(() -> new Exception("Ingredient " + item.getKitchenStock().getDescription() + " not found in kitchen stock"));

            if (stock.getAmount() < item.getAmount()) {
                throw new Exception("Not enough " + item.getKitchenStock().getDescription() + " in stock");
            }

            stock.setAmount((int) (stock.getAmount() - item.getAmount()));
            kitchenStockRepo.save(stock);
        }

        System.out.println(menuItemName + " order placed successfully.");
    }


}

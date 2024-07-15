package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.MenuItemStock;
import com.molveno.restaurantReservation.models.OrderItem;
import com.molveno.restaurantReservation.repos.KitchenStockRepo;
import com.molveno.restaurantReservation.repos.MenuItemStockRepo;
import com.molveno.restaurantReservation.repos.MenuRepo;
import com.molveno.restaurantReservation.repos.OrderItemRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MenuItemStockServiceImpl implements MenuItemStockService {
    @Autowired
    private MenuRepo menuRepo;

    @Autowired
    private KitchenStockRepo kitchenStockRepo;

    @Autowired
    private MenuItemStockRepo menuItemStockRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

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


    @Override
    @Transactional
    public void placeOrder(Long orderItemId) throws Exception {
        Optional<OrderItem> orderItem = orderItemRepo.findById(orderItemId);




    }


}

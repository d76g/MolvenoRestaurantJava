package com.molveno.restaurantReservation.services;

import com.molveno.restaurantReservation.models.DTO.MenuDTO;
import com.molveno.restaurantReservation.models.DTO.MenuStockDTO;
import com.molveno.restaurantReservation.models.DTO.Response.MenuStockResponseDTO;
import com.molveno.restaurantReservation.models.DTO.StockDTO;
import com.molveno.restaurantReservation.models.KitchenStock;
import com.molveno.restaurantReservation.models.Menu;
import com.molveno.restaurantReservation.models.MenuItemStock;
import com.molveno.restaurantReservation.repos.KitchenStockRepo;
import com.molveno.restaurantReservation.repos.MenuItemStockRepo;
import com.molveno.restaurantReservation.repos.MenuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MenuItemStockServiceImpl implements MenuItemStockService {
    
    @Autowired
    private MenuItemStockRepo menuItemStockRepo;

    @Autowired
    private MenuRepo menuRepository;

    @Autowired
    private KitchenStockRepo kitchenStockRepository;
    @Override
    public MenuStockResponseDTO  saveMenuStockItem(MenuStockDTO menuStockDTO) {
        Menu menu = menuRepository.findById(menuStockDTO.getMenuId()).get();

        KitchenStock kitchenStock = kitchenStockRepository.findById(menuStockDTO.getKitchenStockId()).get();

        MenuItemStock menuItemStock = convertToEntity(menuStockDTO, menu, kitchenStock);
        MenuItemStock savedMenuItemStock = menuItemStockRepo.save(menuItemStock);

        return convertToResponseDTO(savedMenuItemStock);
    }

    @Override
    public Iterable<MenuStockResponseDTO> getAllMenuItemStocks() {
        Iterable<MenuItemStock> menuItemStocks = menuItemStockRepo.findAll();
        return ((List<MenuItemStock>) menuItemStocks).stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public Iterable<MenuStockResponseDTO> getAllByMenuItemId(long menu_id) {
        Iterable<MenuItemStock> menuItemStocks = menuItemStockRepo.findAllByMenu_MenuItem_id(menu_id);
        return StreamSupport.stream(menuItemStocks.spliterator(), false)
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MenuItemStock getMenuItemStockById(Long menuItemStock_id) {
        return null;
    }

    @Override
    public void deleteMenuItemStock(Long menuItemStock_id) {
        menuItemStockRepo.deleteById(menuItemStock_id);
    }

    @Override
    public MenuItemStock findById(Long id) {
        return null;
    }
    public MenuItemStock convertToEntity(MenuStockDTO dto, Menu menu, KitchenStock kitchenStock) {
        MenuItemStock entity = new MenuItemStock();
        entity.setId(dto.getId());
        entity.setMenu(menu);
        entity.setKitchenStock(kitchenStock);
        entity.setAmount(dto.getAmount());
        return entity;
    }

    public MenuStockDTO convertToDTO(MenuItemStock entity) {
        MenuStockDTO dto = new MenuStockDTO();
        dto.setId(entity.getId());
        dto.setMenuId(entity.getMenu().getMenuItem_id());
        dto.setKitchenStockId(entity.getKitchenStock().getId());
        dto.setAmount(entity.getAmount());
        return dto;
    }

    public MenuDTO convertToMenuDTO(Menu menu) {
        MenuDTO dto = new MenuDTO();
        dto.setMenuItem_id(menu.getMenuItem_id());
        dto.setItem_name(menu.getItem_name());
        dto.setDescription(menu.getDescription());
        dto.setPrice(menu.getPrice());
        return dto;
    }
    public StockDTO convertToStockDTO(KitchenStock kitchenStock) {
        StockDTO dto = new StockDTO();
        dto.setId(kitchenStock.getId());
        dto.setDescription(kitchenStock.getDescription());
        dto.setStock(kitchenStock.getStock());
        dto.setAmount(kitchenStock.getAmount());
        dto.setUnit(kitchenStock.getUnit());
        return dto;
    }

    public MenuStockResponseDTO convertToResponseDTO(MenuItemStock entity) {
        MenuStockResponseDTO dto = new MenuStockResponseDTO();
        dto.setId(entity.getId());
        dto.setAmount(entity.getAmount());
        dto.setMenu(convertToMenuDTO(entity.getMenu()));
        dto.setKitchenStock(convertToStockDTO(entity.getKitchenStock()));
        return dto;
    }


}

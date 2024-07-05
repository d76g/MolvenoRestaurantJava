package com.molveno.restaurantReservation.models.DTO;

import com.molveno.restaurantReservation.models.KitchenCategory;
import com.molveno.restaurantReservation.models.KitchenStock;

public class StockConverter {
    public static StockDTO toDTO(KitchenStock kitchenStock) {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setId(kitchenStock.getId());
        stockDTO.setDescription(kitchenStock.getDescription());
        stockDTO.setAmount(kitchenStock.getAmount());
        stockDTO.setUnit(kitchenStock.getUnit());
        stockDTO.setBrand(kitchenStock.getBrand());
        stockDTO.setSupplier(kitchenStock.getSupplier());
        stockDTO.setArticleNumber(kitchenStock.getArticleNumber());
        stockDTO.setPrice(kitchenStock.getPrice());
        stockDTO.setTax(kitchenStock.getTax());
        stockDTO.setPricePerUnit(kitchenStock.getPricePerUnit());
        stockDTO.setStock(kitchenStock.getStock());
        stockDTO.setStockValue(kitchenStock.getStockValue());
        stockDTO.setLimit(kitchenStock.getLimit());

        StockCategoryDTO stockCategoryDTO = new StockCategoryDTO();
        stockCategoryDTO.setId(kitchenStock.getCategory().getCategory_id());
        stockCategoryDTO.setCategoryName(kitchenStock.getCategory().getCategoryName());
        stockDTO.setCategory(stockCategoryDTO);
        return stockDTO;
    }

    public static  KitchenStock toEntity (StockDTO dto, KitchenCategory category){
        KitchenStock kitchenStock = new KitchenStock();
        kitchenStock.setId(dto.getId());
        kitchenStock.setDescription(dto.getDescription());
        kitchenStock.setAmount(dto.getAmount());
        kitchenStock.setUnit(dto.getUnit());
        kitchenStock.setBrand(dto.getBrand());
        kitchenStock.setSupplier(dto.getSupplier());
        kitchenStock.setArticleNumber(dto.getArticleNumber());
        kitchenStock.setPrice(dto.getPrice());
        kitchenStock.setTax(dto.getTax());
        kitchenStock.setPricePerUnit(dto.getPricePerUnit());
        kitchenStock.setStock(dto.getStock());
        kitchenStock.setStockValue(dto.getStockValue());
        kitchenStock.setLimit(dto.getLimit());
        kitchenStock.setCategory(category);
        return kitchenStock;
    }
}

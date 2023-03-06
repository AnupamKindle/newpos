package com.kindlebit.pos.service;

import com.kindlebit.pos.dto.QuantityDTO;
import com.kindlebit.pos.models.Pantry;

import java.util.List;

public interface PantryService {

    Pantry storeNewItem(Pantry pantry);

    boolean deleteItem(Long itemId);

    Pantry updateItem(Long itemId,Pantry pantry);

    List<Pantry> getAllItems();

    Pantry fetchItem(Long pantryId,Integer quantity);

    Integer toKnowQuantity(String itemName);

    List<QuantityDTO> lessQuantityInPantry();

    List<Pantry> listOfAllItems(String search);
}

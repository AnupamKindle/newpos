package com.kindlebit.pos.service;

import com.kindlebit.pos.dto.QuantityDTO;
import com.kindlebit.pos.models.Pantry;
import com.kindlebit.pos.utill.Response;

import java.util.List;

public interface PantryService {

    Response storeNewItem(Pantry pantry);

    boolean deleteItem(Long itemId);

    Response updateItem(Long itemId,Pantry pantry);

    List<Pantry> getAllItems();

    Response fetchItem(Long pantryId,Integer quantity);

    Integer toKnowQuantity(String itemName);

    List<QuantityDTO> lessQuantityInPantry();

    List<Pantry> listOfAllItems(String search);
}

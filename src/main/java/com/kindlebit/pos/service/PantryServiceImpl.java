package com.kindlebit.pos.service;


import com.kindlebit.pos.dto.QuantityDTO;
import com.kindlebit.pos.models.Pantry;
import com.kindlebit.pos.repository.PantryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PantryServiceImpl implements PantryService{

    @Autowired
    PantryRepository pantryRepository;

    @Override
    public Pantry storeNewItem(Pantry pantry) {

        Pantry pantryDb=new Pantry();

        Optional<Pantry> existingItem =pantryRepository.findByItemName(pantry.getItemName());
        if(existingItem.isPresent()) {

        throw  new RuntimeException(" This Item is already in pantry ");
        }

        pantryDb.setCreatedAt(new Date());
        pantryDb.setItemName(pantry.getItemName());
        pantryDb.setQuantity(pantry.getQuantity());
        pantryDb.setRackLocation(pantry.getRackLocation());
        Pantry pantryResponse =   pantryRepository.save(pantryDb);
        return pantryResponse;
    }

    @Override
    public boolean deleteItem(Long itemId) {


        return false;
    }

    @Override
    public Pantry updateItem(Long itemId, Pantry pantry) {
        return null;
    }

    @Override
    public List<Pantry> getAllItems() {
        return null;
    }

    @Override
    public Pantry fetchItem(String itemName, Integer quantity) {
        Optional<Pantry> pantry=pantryRepository.findByItemName(itemName);

        Pantry updateQuantity=new Pantry();

        updateQuantity.setId(pantry.get().getId());
        updateQuantity.setUpdatedAt(new Date());
        updateQuantity.setRackLocation(pantry.get().getRackLocation());
        Integer newQuantity=pantry.get().getQuantity()-quantity;

        if(newQuantity<0)
        {
            throw new RuntimeException(" Not enough quantity available in pantry");
        }
        updateQuantity.setQuantity(newQuantity);
        updateQuantity.setItemName(pantry.get().getItemName().toLowerCase(Locale.ROOT));
        updateQuantity.setCreatedAt(pantry.get().getCreatedAt());
        pantryRepository.save(updateQuantity);
        return updateQuantity;
    }

    @Override
    public Integer toKnowQuantity(String itemName) {

        Optional<Pantry> pantry=pantryRepository.findByItemName(itemName);
        Integer presentQuantity=pantry.get().getQuantity();
        return presentQuantity;
    }



    @Override
    public List<QuantityDTO> lessQuantityInPantry() {

        List<Pantry> lowQuantityList= pantryRepository.getAllLowQuantity();
        List<QuantityDTO> quantityDTOS=new ArrayList<>();

        for (Pantry p:lowQuantityList)
        {
            QuantityDTO quantityDTO=new QuantityDTO();
            quantityDTO.setItemName(p.getItemName());
            quantityDTO.setQuantity(p.getQuantity());
            quantityDTOS.add(quantityDTO);
        }
        return quantityDTOS;
    }



}

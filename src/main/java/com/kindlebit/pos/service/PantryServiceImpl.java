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

        Optional<Pantry> existingItem =pantryRepository.findByItemName(pantry.getItemName().toLowerCase());
        if(existingItem.isPresent()) {

        throw  new RuntimeException(" This Item is already in pantry ");
        }

        pantryDb.setCreatedAt(new Date());
        pantryDb.setItemName(pantry.getItemName().toLowerCase());
        pantryDb.setQuantity(pantry.getQuantity());
        pantryDb.setRackLocation(pantry.getRackLocation());
        Pantry pantryResponse =   pantryRepository.save(pantryDb);
        return pantryResponse;
    }

    @Override
    public boolean deleteItem(Long itemId) {

        Optional<Pantry>  existingItem= pantryRepository.findById(itemId);

        pantryRepository.delete(existingItem.get());

        return true;
    }

    @Override
    public Pantry updateItem(Long itemId, Pantry pantry) {

        Optional<Pantry>  existingItem= pantryRepository.findById(itemId);

        Pantry updatedPantry = new Pantry();

        if(!existingItem.isPresent())
        {
            throw new RuntimeException(" Item not found ..");
        }else {

            Long id= existingItem.get().getId();
            int quantity= (pantry.getQuantity()!=0?pantry.getQuantity():existingItem.get().getQuantity());

            String rackLocation = (pantry.getRackLocation()!=null && pantry.getRackLocation() !=" "?pantry.getRackLocation():existingItem.get().getRackLocation());

            Date createdDate = existingItem.get().getCreatedAt();

            Date updatedDate =new Date();

            String itemName =(pantry.getItemName()!= null && pantry.getItemName()!=" "? pantry.getItemName():existingItem.get().getItemName());


            updatedPantry.setId(id);
            updatedPantry.setItemName(itemName);
            updatedPantry.setUpdatedAt(updatedDate);
            updatedPantry.setCreatedAt(createdDate);
            updatedPantry.setRackLocation(rackLocation);
            updatedPantry.setQuantity(quantity);

            pantryRepository.save(updatedPantry);
            return updatedPantry;

        }
    }

    @Override
    public List<Pantry> getAllItems() {
        List<Pantry> pantryList =pantryRepository.findAll();
        return pantryList;
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

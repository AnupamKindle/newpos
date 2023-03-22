package com.kindlebit.pos.service;


import com.kindlebit.pos.dto.QuantityDTO;
import com.kindlebit.pos.models.Pantry;
import com.kindlebit.pos.repository.PantryRepository;
import com.kindlebit.pos.utill.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PantryServiceImpl implements PantryService{

    @Autowired
    PantryRepository pantryRepository;

    @Override
    public Response storeNewItem(Pantry pantry) {

        Pantry pantryDb=new Pantry();

        Response response=new Response();

        Optional<Pantry> existingItem =pantryRepository.findByItemName(pantry.getItemName().toLowerCase());
        if(existingItem.isPresent()) {

            response.setStatusCode(403);
            response.setMessage("This Item is already in pantry");
            response.setBody(null);
            return response;

        }

        pantryDb.setCreatedAt(new Date());
        pantryDb.setItemName(pantry.getItemName().toLowerCase());
        pantryDb.setQuantity(pantry.getQuantity());
        pantryDb.setRackLocation(pantry.getRackLocation().toLowerCase());
        pantryDb.setMinimumQuantity(pantry.getMinimumQuantity());
        Pantry pantryResponse =   pantryRepository.save(pantryDb);
        response.setStatusCode(200);
        response.setBody(pantryResponse);
        response.setMessage("A new Item has been added to Pantry");
        return response;
    }

    @Override
    public boolean deleteItem(Long itemId) {

        Optional<Pantry>  existingItem= pantryRepository.findById(itemId);

        pantryRepository.delete(existingItem.get());

        return true;
    }

    @Override
    public Response updateItem(Long itemId, Pantry pantry) {

        Optional<Pantry>  existingItem= pantryRepository.findById(itemId);

        Pantry updatedPantry = new Pantry();
        Response response=new Response();

        if(!existingItem.isPresent())
        {
            response.setMessage("Item not found in pantry ");
            response.setBody(null);
            response.setStatusCode(404);
            return  response;
        }else {

            Long id= existingItem.get().getId();
            int quantity= (pantry.getQuantity()!=0?pantry.getQuantity():existingItem.get().getQuantity());

            String rackLocation = (pantry.getRackLocation()!=null && pantry.getRackLocation() !=" "?pantry.getRackLocation():existingItem.get().getRackLocation());

            Date createdDate = existingItem.get().getCreatedAt();

            Date updatedDate =new Date();

            String itemName =(pantry.getItemName()!= null && pantry.getItemName()!=" "? pantry.getItemName():existingItem.get().getItemName());

            Optional<Pantry> presentItem = pantryRepository.findByItemName(itemName);

            if(presentItem.get().getId() != id )
            {
                response.setStatusCode(403);
                response.setBody(null);
                response.setMessage("This name of the item is already exists !! ");
                return  response;
            }

            int minimumQuantity= (pantry.getMinimumQuantity()!=0?pantry.getMinimumQuantity():existingItem.get().getMinimumQuantity());

            updatedPantry.setId(id);
            updatedPantry.setItemName(itemName.toLowerCase());
            updatedPantry.setUpdatedAt(updatedDate);
            updatedPantry.setCreatedAt(createdDate);
            updatedPantry.setRackLocation(rackLocation.toLowerCase());
            updatedPantry.setQuantity(quantity);
            updatedPantry.setMinimumQuantity(minimumQuantity);

            pantryRepository.save(updatedPantry);

            response.setStatusCode(200);
            response.setBody(updatedPantry);
            response.setMessage(" Item has been updated ");

            return response;

        }
    }

    @Override
    public List<Pantry> getAllItems() {
        List<Pantry> pantryList =pantryRepository.findAll();
        return pantryList;
    }

    @Override
    public Response fetchItem(Long pantryId, Integer quantity) {
        Optional<Pantry> pantry=pantryRepository.findById(pantryId);

        Response response=new Response();

        Pantry updateQuantity=new Pantry();

        updateQuantity.setId(pantry.get().getId());
        updateQuantity.setUpdatedAt(new Date());
        updateQuantity.setRackLocation(pantry.get().getRackLocation());
        updateQuantity.setMinimumQuantity(pantry.get().getMinimumQuantity());
        Integer newQuantity=pantry.get().getQuantity()-quantity;

        if(newQuantity<0)
        {
            response.setMessage("Not enough quantity available in pantry");
            response.setBody(null);
            response.setStatusCode(404);
            return response;
        }
        updateQuantity.setQuantity(newQuantity);
        updateQuantity.setItemName(pantry.get().getItemName().toLowerCase(Locale.ROOT));
        updateQuantity.setCreatedAt(pantry.get().getCreatedAt());
        updateQuantity.setUpdatedAt(new Date());
        pantryRepository.save(updateQuantity);
        response.setMessage(" A new Quantity has been updated ");
        response.setBody(updateQuantity);
        response.setStatusCode(200);
        return response;
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

    @Override
    public List<Pantry> listOfAllItems(String search) {
        if (search == null) {
            List<Pantry> pantryList = pantryRepository.findAll();

            return pantryList;
        } else if (search != null) {
            List<Pantry> pantryList = pantryRepository.searchByName(search);

            return pantryList;
        }
        return null;
    }





}

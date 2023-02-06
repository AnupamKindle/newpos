package com.kindlebit.pos.controllers;


import com.kindlebit.pos.dto.QuantityDTO;
import com.kindlebit.pos.models.Pantry;
import com.kindlebit.pos.models.TableTop;
import com.kindlebit.pos.service.PantryService;
import com.kindlebit.pos.utill.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/pantry")
public class PantryController {

    @Autowired
    PantryService pantryService;

@PostMapping("/add-new-item-in-pantry")
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
public Response newTable(@RequestBody Pantry pantry) {

    Pantry pantryBody=pantryService.storeNewItem(pantry);
    Response response = new Response();
    response.setBody(pantryBody);
    response.setStatusCode(200);
    response.setMessage(" A new Item has been added to pantry ");
    return response;
}

@GetMapping("/fetch-item")
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public  Response fetchItem(@RequestParam String name,@RequestParam Integer quantity)
{

    Pantry pantryBody=pantryService.fetchItem(name,quantity);
    Response response = new Response();
    response.setBody(pantryBody);
    response.setStatusCode(200);
    response.setMessage(" Item has been updated");
    return response;

}

@GetMapping("/to-know-quantity")
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response knowQuantity(@RequestParam String name)
{

    Integer presentQuantity=pantryService.toKnowQuantity(name);
    Response response = new Response();
    response.setBody(presentQuantity);
    response.setStatusCode(200);
    response.setMessage(" Quantity present for  "+name +" is  "+presentQuantity);
    return response;

}


    @GetMapping("/lit-of-low-quantity")
    //@Scheduled(fixedDelay = 1000, initialDelay = 1000)
    //@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Response lowQuantityItems()
    {
        Response response = new Response();
        try {


            List<QuantityDTO> quantityDTOS = pantryService.lessQuantityInPantry();

            response.setBody(quantityDTOS);
            response.setStatusCode(200);
            response.setMessage(" These are the items which are low in quantity pantry ");
            return response;
        }
        catch (Exception e)
        {
e.printStackTrace();

        }
        return response;
    }




}
